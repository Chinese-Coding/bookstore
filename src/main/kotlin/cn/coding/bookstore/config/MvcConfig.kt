package cn.coding.bookstore.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableAspectJAutoProxy
@Configuration
class MvcConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        val map = HashMap<String, String>().apply {
            /** 基本界面 **/
            this["/"] = "index"
            // this["/login"] = "login" // 单独交给 `LoginController` 处理
            this["/register"] = "register"
            this["/error"] = "error"

            /** 管理员界面 **/
            this["/manager/index"] = "manager/index"
            /// 管理用户界面
            this["/manager/user"] = "manager/user"
            // 修改用户界面因为需要传递参数, 放到 `UserController` 中去了
            /// 管理图书界面
            this["/manager/book"] = "manager/book"
            this["/manager/book/add"] = "manager/book_add"
            // 修改图书界面因为需要传递参数, 放到 `BookController` 中去了
            /// 管理员自己的界面
            this["/manager/mine"] = "manager/mine"
            this["/manager/password"] = "manager/password"
        }

        for ((key, value) in map)
            registry.addViewController(key).setViewName(value)
    }
}