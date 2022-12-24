package com.example.habitualize.ui.notification

data class NotificationModel(
    var time: String = "12:00:PM",
    var title: String = "What’s your Habitualizer?",
    var body: String = "It’s time to check new Tasks!"
)