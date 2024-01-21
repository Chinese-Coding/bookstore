package cn.coding.bookstore.entity

data class User(
    var userId: Int?,
    var username: String,
    var password: String?,
    var name: String,
    var email: String,
    var age: Int,
    var gender: String,
    var role: String
)
