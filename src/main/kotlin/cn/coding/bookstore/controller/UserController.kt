package cn.coding.bookstore.controller

import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.entity.User
import cn.coding.bookstore.mapper.UserMapper
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import jakarta.annotation.Resource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
class UserController {
    @Resource
    private lateinit var userMapper: UserMapper

    /**
     * 查询所有用户信息, 除了管理员本人信息
     */
    @ResponseBody
    @GetMapping(value = ["/api/manager/user"], produces = ["application/json"])
    fun allShow(@SessionAttribute(name = "now_user") adminUser: User): List<User> {
        return userMapper.selectAllUser(adminUser.userId)
    }

    /**
     * 分页查询
     */
    @ResponseBody
    @GetMapping("/api/manager/user/{pageNum}")
    fun pageShow(@PathVariable pageNum: Int, @SessionAttribute(name = "now_user") adminUser: User): PageInfo<User> {
        PageHelper.startPage<User>(pageNum, 8) // 很有意思, 原项目中不需要指定 E 的类型, 而在 kotlin 中却需要
        val users = userMapper.selectAllUser(adminUser.userId)
        return PageInfo(users, 3)
    }

    /**
     * 删除用户信息
     */
    @ResponseBody
    @DeleteMapping("/api/manager/user/{userId}")
    fun remove(@PathVariable userId: Int): ResponseObject {
        val user = userMapper.selectById(userId)
        if (userMapper.delete(userId) > 0)
            return ResponseObject("删除成功", 200, Pair(user.userId, user.username))
        return ResponseObject("删除失败", 400, Pair(user.userId, user.username))
    }

    /**
     * 获取修改用户信息页面
     */
    @GetMapping("/manager/user/modify/{userId}")
    fun getModifyView(@PathVariable userId: Int): ModelAndView {
        val user = userMapper.selectById(userId)
        val mv = ModelAndView()
        mv.viewName = "manager/user_modify"
        mv.addObject("user", user)
        mv.addObject("userId", userId)
        return mv
    }

    /**
     * 修改用户信息
     */
    @ResponseBody
    @PostMapping("/api/manager/user/modify/{userId}")
    fun modifyUserInfo(@PathVariable userId: Int, user: User): ResponseObject {
        if (userMapper.update(user, userId) > 0)
            return ResponseObject("修改成功", 200, Pair(userId, user.username))
        return ResponseObject("修改失败", 400, Pair(userId, user.username))
    }
}