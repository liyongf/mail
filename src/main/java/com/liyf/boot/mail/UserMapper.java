package com.liyf.boot.mail;
import com.liyf.boot.entity.UserAliase;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
    public interface UserMapper {

        @Select("select id,username,password,realname from t_s_base_user where is_delete='0'")
        List<UserAliase> getAll();

        @Select("select * from user where username=#{username}")
        UserAliase getByUsername(String username);

        @Insert("insert into user(username,password) valuses(#{username},#{password})")
        void insert(UserAliase user);

        @Delete("delete from user where username=#{username}")
        void delete(String username);

        @Update("update t_s_base_user set password=#{password} where username=#{username}")
        void changePassword(@Param("username")String username,@Param("password")String password);


}
