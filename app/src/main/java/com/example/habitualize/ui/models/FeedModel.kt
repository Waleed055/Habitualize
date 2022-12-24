package com.example.habitualize.ui.models

data class FeedModel(
    var feed_id: String = "",
    var user_image: String = "",
    var user_id: String = "",
    var user_name: String = "",
    var question: String = "",
    var answer: String = "",
    var likes: ArrayList<String> = arrayListOf(),
    var flowers_counter: String = "0",
    var hug_counter: String = "0",
    var high_five_counter: String = "0",
    var love_counter: String = "0",
    var appreciation_counter: String = "0",
    var thank_you_counter: String = "0",
    var reported: Boolean = false,
    var created_at: String = "0",
    var language_code: String = "en",
    var comments_list: ArrayList<FeedCommentsModel> = arrayListOf()
)
data class FeedCommentsModel(
    var user_image: String = "",
    var user_id: String = "",
    var user_name: String = "",
    var created_at: String = "",
    var comment: String = ""
)
