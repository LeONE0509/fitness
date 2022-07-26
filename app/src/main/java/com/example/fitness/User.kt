package com.example.fitness

data class User(val place: Int,
                val imageId: Int,
                val name: String,
                val steps: Int,
                val itsMe: Boolean = false)
