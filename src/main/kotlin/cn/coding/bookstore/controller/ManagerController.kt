package cn.coding.bookstore.controller

import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.entity.User
import cn.coding.bookstore.mapper.UserMapper
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ManagerController {
    @Resource
    private lateinit var userMapper: UserMapper


    @PostMapping("/api/manager/mine/modify")
    fun modifyInfo(user: User, session: HttpSession): ResponseObject {
        val nowUser = session.getAttribute("now_user") as User
        val userId = nowUser.userId as Int
        if (userMapper.update(user, userId) > 0) {
            session.setAttribute("now_user", userMapper.selectById(userId))
            return ResponseObject("修改个人信息成功", 200, Pair(userId, user.name))
        } else
            return ResponseObject("修改个人信息失败", 400, Pair(userId, user.name))
    }

    @PostMapping(value = ["/api/manager/password/modify/{userId}"], produces = ["application/json"])
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