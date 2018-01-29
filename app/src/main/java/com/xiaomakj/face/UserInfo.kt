package com.xiaomakj.face

/**
 * Created by MaQi on 2018/1/29.
 */

/**
 *
//    {
//        "phone": "15171462306",
//        "name": "much",
//        "email": "",
//        "avatar": "https://dn-spapi1.qbox.me/avatar/2018/01/29/o8h67hnibq7bct7u.jpg",
//        "regioncode": "86",
//        "persona": {
//        "gender": "male",
//        "tags": ["eyeglasses"],
//        "location": {
//        "country": "CN",
//        "province": "湖北",
//        "city": "武汉"
//    },
//        "generation": "10s",
//        "character": ""
//    },
//        "group_uid": "ad237542b963ea50cd8277357d360106"
//    }
 */
data class UserInfo(var phone: String,
                    var name: String,
                    var email: String,
                    var avatar: String,
                    var regioncode: String,
                    var persona: Persona,
                    var group_uid: String) {
    data class Persona(var gender: String,
                       var location: Location,
                       var generation: String,
                       var character: String,
                       var tags: List<String>) {
        data class Location(var country: String,
                            var province: String,
                            var city: String)
    }
}