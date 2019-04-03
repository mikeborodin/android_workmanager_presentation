package com.example.workmanagertutorial.entity

import java.io.Serializable


class Media(val url: String, val tags: List<String> = listOf(), var id: String = "") : Serializable