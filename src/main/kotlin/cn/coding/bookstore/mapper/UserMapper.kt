package cn.coding.bookstore.mapper

import cn.coding.bookstore.entity.User
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {
    @Select("select * from user where username = #{username} and password = #{password}")
    fun login(@Param("username") username: String, @Param("password") password: String): User?

    fun register(@Param("user") user: User): Int

    fun insert(user: User): Int

    @Delete("delete from user where user_id = #{userId}")
    fun delete(userId: Int): Int

    @Update("update user set password = #{password} where user_id = #{userId}")
    fun updatePassword(@Param("userId") userId: Int, @Param("password") password: String): Int

    fun update(@Param("user") user: User, @Param("userId") userId: Int): Int

    /**
     * 从数据库中查询所有非管理员权限的用户
     * */
    @Select("select * from user where user_id != #{adminUserId}")
    fun selectAllUser(adminUserId: Int): List<User>

    @Select("select * from user where username = #{username}")
    fun selectByUsername(username: String): User?

    @Select("select * from user where user_id = #{userId}")
    fun selectById(userId: Int): User
}