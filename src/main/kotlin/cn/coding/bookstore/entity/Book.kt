package cn.coding.bookstore.entity

data class Book(
    var bookId: Int?,
    val title: String,
    val author: String,
    val price: Double,
    val category: String,
    val description: String,
    val sales: Int,
    val stock: Int,
)