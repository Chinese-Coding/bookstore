package cn.coding.bookstore.controller

import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.entity.User
import cn.coding.bookstore.mapper.UserMapper
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.SessionAttribute

@Controller
class LoginController {
    @Resource
    private lateinit var userMapper: UserMapper

    /**
     * 判断是否已经登录
     *
     * @return 判断 `session` 中的 `now_user` 是否为空, 跳转到对应页面
     */
    @GetMapping("/login")
    fun login(
        @SessionAttribute(name = "now_user", required = false) nowUser: User?,
        request: HttpServletRequest
    ): String {
        if (nowUser != null) {
            if (nowUser.role == "管理员")
                return "redirect:/manager/index"
            else if (nowUser.role == "普通用户")
                return "redirect:/common/index"
        }
        return "login"
    }

    /**
     * 登录接口
     * */
    @ResponseBody
    @PostMapping(value = ["/api/login"], produces = ["application/json"])
    fun login(
        username: String, password: String, session: HttpSession,
        request: HttpServletRequest
    ): ResponseObject {
        if (session.getAttribute("now_user") == null) {
            val user = userMapper.login(username, password)
            if (user != null) {
                session.setAttribute("now_user", user) // 将用户数据共享至 session 域
                val path = when (user.role) {
                    "管理员" -> "/manager/index"
                    "普通用户" -> "/common/index"
                    else -> "/login"
                }
                return ResponseObject(path, 200, Pair(user.userId, user.username)) // 复用 msg 传递转移参数
            } else
                return ResponseObject("登录失败", 400, "/login") // 消息码 400 表示用户名或密码输入错误
        } else {
            val user = session.getAttribute("now_user") as User
            return ResponseObject(
                "同一个浏览器同时只能一个账号登录，请退出前一个账号后再重试",
                101,
                Pair(user.userId, user.username)
            )
        }
    }
}