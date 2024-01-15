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
            // 基本界面
            this["/"] = "index"
            // this["/login"] = "login" 单独交给 `LoginController` 处理
            this["/register"] = "register"
            this["/error"] = "error"
        }

        for ((key, value) in map)
            registry.addViewController(key).setViewName(value)
    }
}