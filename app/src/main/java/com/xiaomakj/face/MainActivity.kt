package com.xiaomakj.face

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.isnc.facesdk.SuperID
import com.isnc.facesdk.common.Cache
import com.isnc.facesdk.common.SDKConfig
import com.networkbench.agent.impl.NBSAppAgent
import com.pascalwelsch.extensions.launchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesUtil.init(this,packageName, Context.MODE_PRIVATE)
        Log.i("MainActivity", sHA1(this))
        NBSAppAgent.setLicenseKey("b916676062a64ec58dc343ff7f6b7cc8 ").withLocationServiceEnabled(true).start(this.applicationContext)

        SuperID.initFaceSDK(this)
        SuperID.setDebugMode(true)
        SuperID.isUidAuthorized(this, SharedPreferencesUtil.getInstance().getString("openid"), { result ->
            when (result) {
                SDKConfig.ISAUTHORIZED -> Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show()
                SDKConfig.NOAUTHORIZED -> Toast.makeText(this, "未授权", Toast.LENGTH_SHORT).show()
                else -> {
                }
            }
        }) { error ->
            when (error) {
                SDKConfig.APPTOKEN_EXPIRED -> Toast.makeText(this, "用户已经太久没操作，请重新登录", Toast.LENGTH_SHORT).show()
                SDKConfig.OTHER_ERROR -> Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show()
                else -> {
                }
            }
        }
        faceLogin.onClick {
            SuperID.faceLogin(this@MainActivity)
        }
        getFaceFeatures.onClick {
            SuperID.getFaceFeatures(this@MainActivity)
        }
        facesDetect.onClick {
            launchActivity<FaceSearchActivity> {  }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            SDKConfig.AUTH_SUCCESS -> {
                //< !--openid 为开发者应用生成的openid ， 若调用faceLogin (this this) 进行注册授权 ， 则系统将会自动生成一个openid -->
                val openid = Cache.getCached(this
                        , SDKConfig.KEY_OPENID)
                //< !--userInfo 为SuperID用户信息 ， 格式为json -->
                val userInfo = Cache.getCached(this, SDKConfig.KEY_APPINFO)
                Toast.makeText(this, "授权成功openid====$openid====userInfo====$userInfo", Toast.LENGTH_SHORT).show()
                Log.i("resultCode", "授权成功openid====$openid====userInfo====$userInfo")
                SharedPreferencesUtil.getInstance().putString("UserInfo",userInfo)
                SharedPreferencesUtil.getInstance().putString("openid",openid)
            }
            SDKConfig.LOGINSUCCESS -> {
                //< !--openid 为开发者应用的openid ， 若用户在调用faceLogin (this this) 进行注册授权 ， 则系统将会自动生成一个openid ， 重新登录成功时返回此openid -->
                val openid = Cache.getCached(this
                        , SDKConfig.KEY_OPENID)
                //< !--userInfo 为SuperID用户信息 ， 格式为json -->
                val userInfo = Cache.getCached(this, SDKConfig.KEY_APPINFO)
                Toast.makeText(this, "登录成功openid====$openid====userInfo====$userInfo", Toast.LENGTH_SHORT).show()
                Log.i("resultCode", "授权成功openid====$openid====userInfo====$userInfo")
                SharedPreferencesUtil.getInstance().putString("UserInfo",userInfo)
                SharedPreferencesUtil.getInstance().putString("openid",openid)
            }
            SDKConfig.GETEMOTIONRESULT -> {
                //< !-- featureInfo 为人脸数据，格式为json -->
                val featureInfo = data.getStringExtra(SDKConfig.FACEDATA)
                Log.i("resultCode", "获取人脸数据成功featureInfo===="+featureInfo)
                SharedPreferencesUtil.getInstance().putString("FeatureInfo",featureInfo)
            }
            else ->
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
        }
    }

    // 授权成功openid====a67205ca4215a30b030c0====userInfo====
    // {"phone":"","name":"much","email":"","avatar":"","regioncode":"86","persona":{"gender":"male",
    // "tags":["eyeglasses"],"location":{"country":"CN","province":"湖北","city":"武汉"},"generation":"10s","character":""},"group_uid":"ad237542b963ea50cd8277357d360106"}
    fun sHA1(context: Context): String? {
        try {
            val info = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_SIGNATURES)
            val cert = info.signatures[0].toByteArray()
            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for (i in publicKey.indices) {
                val appendString = Integer.toHexString(0xFF and publicKey[i].toInt())
                        .toUpperCase(Locale.US)
                if (appendString.length == 1)
                    hexString.append("0")
                hexString.append(appendString)
                hexString.append(":")
            }
            val result = hexString.toString()
            return result.substring(0, result.length - 1)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        SuperID.faceLogout(this)
    }
}
