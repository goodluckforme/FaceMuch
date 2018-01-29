package com.xiaomakj.face

import com.isnc.facesdk.aty.Aty_BaseGroupCompare
import kotlinx.android.synthetic.main.activity_facesearch.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by MaQi on 2018/1/29.
 */

class FaceSearchActivity : Aty_BaseGroupCompare() {
    override fun initView() {
        //初始化多人脸,size为数组，{width，height}设置surfaceview宽高，宽高比例必须为3:4,否则会变形
        //若为null，则为全屛
        val size = intArrayOf(480, 640)
        initFacesFeature(size)
        //设置摄像头 前1 后0
        setCameraType(1)
        toChangeCamera.onClick {
            changeCamera()
        }
    }

    override fun initData() {

    }

    override fun getContentLayoutId(): Int = R.layout.activity_facesearch

    override fun initActions() {
        //开始执行人脸检测，sourceID、groupID参照后端文档获得
        facesDetect("bo1uP6FXgaxlorB9S9SI", "ad237542b963ea50cd8277357d360106")
    }
}
