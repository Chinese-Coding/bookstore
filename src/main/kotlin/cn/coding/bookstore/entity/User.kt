package cn.coding.bookstore.entity

data class User(
    var userId: Int?,
    val username: String,
    val password: String?,
    val name: String,
    val email: String,
    val age: Int,
    val gender: String,
    val role: String
)
