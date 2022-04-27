package com.liyf.boot.mail;
import com.liyf.boot.entity.UserAliase;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
    public interface UserMapper {

        @Select("select*from user")
        List<UserAliase> getAll();

        @Select("select * from user where username=#{username}")
        UserAliase getByUsername(String username);

        @Insert("insert into user(username,password) valuses(#{username},#{password})")
        void insert(UserAliase user);

        @Delete("delete from user where username=#{username}")
        void delete(String username);

        @Update("update user set password=#{password} where username=#{username}")
        void changePassword(String username,String password);


}
