package cn.coding.bookstore.mapper

import cn.coding.bookstore.entity.User
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {
    @Select("select * from user where username = #{username} and password = #{password}")
    fun login(@Param("username") username: String, @Param("password") password: String): User?
}