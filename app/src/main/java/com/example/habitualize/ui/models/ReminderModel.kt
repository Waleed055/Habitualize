package com.example.habitualize.ui.models

data class ReminderModel(
    var reminder: String = "",
    var language_code: String = "",
)

data class AllRemindersModel(
    var opened_reminders: ArrayList<ReminderModel> = arrayListOf(),
    var all_reminders: ArrayList<String> = arrayListOf()
)