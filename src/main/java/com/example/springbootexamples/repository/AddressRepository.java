package com.example.springbootexamples.repository;

import com.example.springbootexamples.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//创建Address实体类对应的持久组件，并继承自定义接口
@Repository
public interface AddressRepository extends CustomizedRepository<Address, Integer>{
    @Query("SELECT a FROM Address a WHERE a.user.id = :uid")
    List<Address> list(@Param("uid") int uid);

    @Query("SELECT a FROM Address a WHERE a.user.id = :uid AND a.id = :aid")
    Address find(@Param("uid") int uid, @Param("aid") int aid);
}
