package com.team.parking.data.model.user

data class User(
    private val user_id : Long,
    private val social_id : String,
    private val phone : String,
    private val email : String,
    private val nickname : String,
    private val fcm_token : String,
    private val profile_img : String,
    private val pt_has : Int
)