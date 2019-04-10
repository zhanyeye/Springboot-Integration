package com.example.springbootexamples.repository;

import com.example.springbootexamples.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//创建User实体类对应的持久组件，并继承自定义接口
@Repository
public interface UserRepository extends CustomizedRepository<User, Integer>{
    @Query("SELECT u from User u WHERE u.userName = :username")
    User findUser(@Param("username") String userName);
}
