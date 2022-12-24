package com.example.habitualize.ui.models

import java.io.Serializable

data class BlogModel(
    var image_link: String ="",
    var title: String ="",
    var link: String ="",
    var isShow: String =""
): Serializable
