package com.example.habitualize.ui.models

data class CommunityChallengeModel(
    var user_id: String = "",
    var user_name: String = "",
    var challenge_id: String = "",
    var challenge_name: String = "",
    var challenge_description: String = "",
    var challenge_emoji: String = "",
    var language_code: String = "",
    var likes: ArrayList<String> = arrayListOf(),
    var challenge_list: ArrayList<String> = arrayListOf()
)