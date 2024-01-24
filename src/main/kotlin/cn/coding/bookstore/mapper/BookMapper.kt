package cn.coding.bookstore.mapper

import cn.coding.bookstore.entity.Book
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select


@Mapper
interface BookMapper {
    @Select("select * from book")
    fun selectAll(): List<Book>

    @Select("select * from book where book_id = #{bookId}")
    fun selectById(bookId: Int): Book

    fun selectByPrice(@Param("left") left: Double?, @Param("right") right: Double?): List<Book>

    @Delete("delete from book where book_id = #{bookId}")
    fun delete(bookId: Int): Int

    fun insert(book: Book): Int

    fun update(book: Book, bookId: Int): Int
}