package com.example.habitualize.ui.models

import java.io.Serializable

data class DailyChallengesModel(
    var id: Long = 0,
    var challenge_name: String = "",
    var challenge_description: String = "",
    var challenge_emoji: String = "",
    var isUserLocal: Boolean = false,
    var isOpened: Boolean = false,
    var challenges: ArrayList<ChallengeDetailModel> = arrayListOf()
) : Serializable