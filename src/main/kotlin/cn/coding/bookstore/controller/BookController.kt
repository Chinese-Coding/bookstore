package cn.coding.bookstore.controller

import cn.coding.bookstore.entity.Book
import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.mapper.BookMapper
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import jakarta.annotation.Resource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.nio.file.Paths
import javax.imageio.ImageIO

@RestController
class BookController {
    @Resource
    private lateinit var bookMapper: BookMapper


    /**
     * 增加书籍, 并上传图片
     * */
    @PostMapping("/api/manager/book/add")
    fun add(book: Book, @RequestParam("image") file: MultipartFile): ResponseObject {
        if (bookMapper.insert(book) < 0)
            return ResponseObject("添加图书失败", 400, book.toString())
        if (!file.isEmpty) {
            // 如果说上传的文件是图片类型, 那么就将其保存为 png 格式
            if (file.contentType in setOf("image/png", "image/jpeg", "image/gif")) {
                val image: BufferedImage = ImageIO.read(ByteArrayInputStream(file.bytes))
                val outputPath = Paths.get("./src/main/resources/static/img/${book.bookId}.png")
                ImageIO.write(image, "png", outputPath.toFile())
            } else return ResponseObject("文件格式错误, 请上传图片文件", 401, book.toString())
        }
        return ResponseObject("添加图书成功", 200, book.toString())
    }

    /**
     * 删除书籍
     * */
    @DeleteMapping("/api/manager/book/{bookId}")
    fun remove(@PathVariable bookId: Int): ResponseObject {
        val book = bookMapper.selectById(bookId)
        if (bookMapper.delete(bookId) > 0)
            return ResponseObject("删除成功", 200, book.toString())
        return ResponseObject("删除失败", 400, book.toString())
    }

    /**
     * 获取修改图书信息界面
     * */
    @GetMapping("/manager/book/modify/{bookId}")
    fun getModifyView(@PathVariable bookId: Int): ModelAndView {
        val book = bookMapper.selectById(bookId)
        val mv = ModelAndView()
        mv.viewName = "manager/book_modify"
        mv.addObject("book", book)
        mv.addObject("bookId", bookId)
        return mv
    }

    /**
     * 修改书籍
     * */
    @PostMapping("/api/manager/book/modify/{bookId}")
    fun modify(@PathVariable bookId: Int, book: Book, @RequestParam("image") file: MultipartFile): ResponseObject {
        if (bookMapper.update(book, bookId) < 0)
            return ResponseObject("修改图书失败", 400, book.toString())
        if (!file.isEmpty) {
            // 如果说上传的文件是图片类型, 那么就将其保存为 png 格式
            if (file.contentType in setOf("image/png", "image/jpeg", "image/gif")) {
                val image: BufferedImage = ImageIO.read(ByteArrayInputStream(file.bytes))
                val outputPath = Paths.get("./src/main/resources/static/img/${book.bookId}.png")
                ImageIO.write(image, "png", outputPath.toFile())
            } else return ResponseObject("文件格式错误, 请上传图片文件", 401, book.toString())
        }
        return ResponseObject("修改图书成功", 200, book.toString())
    }

    /**
     * 展示全部书籍信息
     * */
    @GetMapping(value = ["/api/manager/book"], produces = ["application/json"])
    fun allShow(): List<Book> {
        return bookMapper.selectAll()
    }

    /**
     * 分页展示书籍信息
     * */
    @GetMapping(value = ["/api/manager/book/{pageNumber}"], produces = ["application/json"])
    fun pageShow(@PathVariable pageNumber: Int): PageInfo<Book> {
        PageHelper.startPage<Book>(pageNumber, 5)
        val books = bookMapper.selectAll()
        return PageInfo(books, 3)
    }
}