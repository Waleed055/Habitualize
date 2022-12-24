package com.example.habitualize.ui.models

data class AppTaskModel(
    var id: Long = 0,
    var reward: Int = 0,
    var target: Int = 0,
    var reward_text: String = "",
    var type: String = "",
    var isCompleted: Boolean = false
)