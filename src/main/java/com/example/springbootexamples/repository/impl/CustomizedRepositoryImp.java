package com.example.springbootexamples.repository.impl;

import com.example.springbootexamples.repository.CustomizedRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
//创建自定义接口的实现类，继承SimpleJpaRepository类，从而注入EntityManager对象
//基于em对象，实现refresh()方法
public class CustomizedRepositoryImp<T, ID> extends SimpleJpaRepository<T, ID> implements CustomizedRepository<T, ID> {

    private EntityManager entityManager;

    public CustomizedRepositoryImp(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T refresh(T t) {
        entityManager.refresh(t);
        return t;
    }
}
