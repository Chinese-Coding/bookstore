package cn.coding.bookstore.entity

data class ResponseObject(
    val msg: String,
    val code: Int,
    val data: Any?
)