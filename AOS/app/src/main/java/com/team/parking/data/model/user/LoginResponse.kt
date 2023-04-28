package com.team.parking.data.model.user

data class LoginResponse (val user_id: Long? = null,
                          val name: String? = null,
                          val type: String? = null,

                          val phone: String? = null,
                          val email: String? = null,

                          val profile_img: String? = null,
                          val ptHas: Int = 0) {

    override fun toString(): String {
        return "user_id = ${user_id}," +
                "name = ${name}," +
                "type = ${type}," +
                "phone = ${phone}," +
                "email = $String," +
                "profile_img = $profile_img" +
                "ptHas = $ptHas"
    }
}