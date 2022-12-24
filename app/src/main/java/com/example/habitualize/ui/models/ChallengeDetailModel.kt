package com.example.habitualize.ui.models

import java.io.Serializable

data class ChallengeDetailModel(
    var id: Long = 0,
    var challenge: String = "",
    var challenge_name: String = "",
    var openDate: Long = 0,
    var isOpened: Boolean = false
): Serializable