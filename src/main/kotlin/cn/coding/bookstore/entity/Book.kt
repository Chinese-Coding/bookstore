package cn.coding.bookstore.entity

data class Book(
    var bookId: Int?,
    var title: String,
    var author: String,
    var price: Double,
    var category: String,
    var description: String,
    var sales: Int,
    var stock: Int,
)