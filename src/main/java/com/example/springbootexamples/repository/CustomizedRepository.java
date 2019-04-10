package com.example.springbootexamples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
//自定义支持refresh()方法的全局基本接口，声明@NoRepositoryBean注解，禁止spring创建按组件创建对象，声明refresh()方法
@NoRepositoryBean
public interface CustomizedRepository<T, ID> extends JpaRepository<T, ID> {
    T refresh(T t);
}
