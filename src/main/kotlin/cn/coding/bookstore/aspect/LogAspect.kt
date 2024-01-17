package cn.coding.bookstore.aspect

import cn.coding.bookstore.controller.LoginController
import cn.coding.bookstore.controller.UserController
import cn.coding.bookstore.entity.ResponseObject
import cn.coding.bookstore.utils.getIpAddr
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 日志切片
 * */
@Component
@Aspect
class LogAspect {
    @Resource
    private lateinit var request: HttpServletRequest

    /**
     * 记录登录日志.
     *
     * @param result 登录函数返回给前端的返回值
     * @return 无返回值, 输出登录 IP, 登录状态, 登录者的 `username `和 `userId`
     * */
    @AfterReturning(
        "execution(* cn.coding.bookstore.controller.LoginController.login(String, String, ..))",
        returning = "result"
    )
    fun loginLog(result: ResponseObject) {
        val logger = LoggerFactory.getLogger(LoginController::class.java)
        val ip = getIpAddr(request)
        val log = when (result.code) {
            200 -> {
                val data = result.data as Pair<*, *>
                "${data.second}(${data.first}) 登录成功, IP: $ip"
            }

            400 -> "登录失败, IP: $ip"
            else -> {
                val data = result.data as Pair<*, *>
                "${data.second}(${data.first}) 重复登录, IP: $ip"
            }
        }
        logger.info("\u001B[33m--- $log ---\u001B[0m") // 在控制台上输出黄色的日志信息
    }

    @AfterReturning(
        "execution(* cn.coding.bookstore.controller.UserController.remove(..))",
        returning = "result"
    )
    fun userDeleteLog(result: ResponseObject) {
        val logger = LoggerFactory.getLogger(UserController::class.java)
        val data = result.data as Pair<*, *>
        val log = when (result.code) {
            200 -> "删除用户 ${data.second}(${data.first}) 成功"
            400 -> "删除用户 ${data.second}(${data.first}) 失败"
            else -> ""
        }
        logger.info("\u001B[33m--- $log ---\u001B[0m") // 在控制台上输出黄色的日志信息
    }

    @AfterReturning(
        "execution(* cn.coding.bookstore.controller.UserController.modifyUserInfo(..))",
        returning = "result"
    )
    fun userUpdateLog(result: ResponseObject) {
        val logger = LoggerFactory.getLogger(UserController::class.java)
        val data = result.data as Pair<*, *>
        val log = when (result.code) {
            200 -> "修改用户 ${data.second}(${data.first}) 成功"
            400 -> "修改用户 ${data.second}(${data.first}) 失败"
            else -> ""
        }
        logger.info("\u001B[33m--- $log ---\u001B[0m") // 在控制台上输出黄色的日志信息
    }
}
