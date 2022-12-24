package com.example.habitualize.ui.models

import com.example.habitualize.ui.notification.NotificationModel

data class UserModel(
    var user_name: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var password: String = "",
    var coins: String = "",
    var profile_image: String = "",
    var notifications_time: ArrayList<NotificationModel> = arrayListOf()
)
