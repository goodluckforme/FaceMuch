package com.xiaomakj.face

/**
 * Created by MaQi on 2018/1/29.
 */
//smiling	【微笑值】 result=1表示微笑，score表示微笑的程度
//male	【性别】 result=1表示男性，score表示为男性的可信度，result=0表示女性
//eyeglasses	【是否戴眼镜】 result=1表示有戴，0表示没戴
//sunglasses	【是否戴太阳眼镜】 result=1表示有戴，0表示没戴
//age	【年龄】
//mustache	【胡须密度】result=1表示有胡须，0表示没胡须，score表示有胡须的程度
//emotions	【表情】包含生气、平静、困惑、愤怒、快乐、悲伤、惊喜7种情绪所占的分值，可以取最大值作为当前人脸的表情
//attractive	【颜值】取值范围为0~1
//orientation	【人脸旋转角】，是人脸眼中间和嘴中间的连线，与垂线的夹角
//blink	【闭眼】 result=1表示闭眼，score表示闭眼的程度
//mouth_open	【嘴巴张开度】 result=1表示嘴巴张开，score表示嘴巴张开的程度
//components	【人脸关键点】
//position	【人脸框】
data class FeatureInfo(var smiling: Smiling,
                       var male: Male,
                       var eyeglasses: Eyeglasses,
                       var sunglasses: Sunglasses,
                       var age: Int,
                       var mustache: Mustache,
                       var resource_id: String) {
    data class Smiling(var result: Int,
                       var score: Int)

    data class Male(var result: Int,
                    var score: Int)

    data class Eyeglasses(var result: Int,
                          var score: Int)

    data class Sunglasses(var result: Int,
                          var score: Int)

    data class Mustache(var result: Int,
                        var score: Double)
}