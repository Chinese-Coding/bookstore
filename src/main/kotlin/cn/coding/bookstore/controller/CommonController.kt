package cn.coding.bookstore.controller

import cn.coding.bookstore.entity.Book
import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.entity.User
import cn.coding.bookstore.mapper.BookMapper
import cn.coding.bookstore.mapper.UserMapper
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.*

@RestController
class CommonController {
    @Resource
    private lateinit var bookMapper: BookMapper

    @Resource
    private lateinit var userMapper: UserMapper

    /**
     * 全部展示
     *
     * 尝试使用 kotlin 的单行函数 (我不清楚这么说是否正确) 写一下
     * */
    @GetMapping(value = ["/api/common/index"], produces = ["application/json"])
    fun allShow(): List<Book> = bookMapper.selectAll()

    /**
     * 分页展示
     * */
    @GetMapping(value = ["/api/common/index/{pageNumber}"], produces = ["application/json"])
    fun pageShow(@PathVariable pageNumber: Int): PageInfo<Book> {
        PageHelper.startPage<Book>(pageNumber, 4)
        return PageInfo(bookMapper.selectAll(), 3)
    }

    /**
     * 根据价格展示
     * */
    @GetMapping(value = ["/api/common/book/{priceLeft}/{priceRight}"], produces = ["application/json"])
    fun priceShow(
        @PathVariable(required = false) priceLeft: Double?,
        @PathVariable(required = false) priceRight: Double?
    ): List<Book> {
        return if (priceLeft == null || priceRight == null)
            bookMapper.selectAll()
        else
            bookMapper.selectByPrice(priceLeft, priceRight)
    }

    /**
     * 普通用户对个人信息的修改
     * */
    @PostMapping("api/common/mine/modify/{userId}")
    fun modifyInfo(@PathVariable userId: Int, user: User, session: HttpSession): ResponseObject {
        if (userMapper.update(user, userId) > 0) {
            session.setAttribute("now_user", userMapper.selectById(userId))
            return ResponseObject("修改成功", 200, Pair(userId, user.username))
        } else
            return ResponseObject("修改失败", 400, Pair(userId, user.username))
    }

    /**
     * 普通用户对自己密码的修改
     * */
    @PostMapping(value = ["/api/common/password/modify/{userId}"], produces = ["application/json"])
    fun modifyPassword(
        @PathVariable userId: Int, @RequestParam oldPassword: String,
        @RequestParam newPassword: String, session: HttpSession
    ): ResponseObject {
        val user = userMapper.selectById(userId)
        if (user.password != oldPassword)
            return ResponseObject("原密码不正确", 401, Pair(user.userId, user.name))
        else {
            if (userMapper.updatePassword(userId, newPassword) > 0) {
                session.invalidate()
                return ResponseObject("修改密码成功", 200, Pair(user.userId, user.name))
            } else
                return ResponseObject("修改密码失败", 400, Pair(user.userId, user.name))
        }
    }
}