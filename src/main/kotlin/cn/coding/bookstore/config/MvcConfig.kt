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
            // ** 基本界面 **
            this["/"] = "index"
            // this["/login"] = "login" // 单独交给 `LoginController` 处理
            this["/register"] = "register"
            this["/error"] = "error"

            /**
             * ** 管理员界面 **
             *
             * * 管理用户界面
             * * 管理图书界面
             * * 管理个人信息界面
             * */
            this["/manager/index"] = "manager/index"
            this["/manager/user"] = "manager/user"
            // 修改用户界面因为需要传递参数, 放到 `UserController` 中去了
            this["/manager/book"] = "manager/book"
            this["/manager/book/add"] = "manager/book_add"
            // 修改图书界面因为需要传递参数, 放到 `BookController` 中去了
            this["/manager/mine"] = "manager/mine"
            this["/manager/password"] = "manager/password"

            /**
             * ** 普通用户界面 **
             *
             * 从上到下依次是: 主页 (展示书籍), 修改个人信息页, 修改密码页, 购物车页
             * */
            this["/common/index"] = "common/index"
            this["common/mine"] = "common/mine"
            this["common/password"] = "common/password"
            this["common/cart"] = "common/cart"
        }

        for ((key, value) in map)
            registry.addViewController(key).setViewName(value)
    }
}