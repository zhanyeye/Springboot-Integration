package com.example.springbootexamples.service;

import com.example.springbootexamples.entity.Address;
import com.example.springbootexamples.entity.User;
import com.example.springbootexamples.repository.AddressRepository;
import com.example.springbootexamples.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    //添加实现addUser()/getUser()/addAddress()/listAddresses()方法
    public User addUser(User u) {
        userRepository.save(u);
        return userRepository.refresh(u);
    }

    public User getUser(String userName) {
        return userRepository.findUser(userName);
    }

    public Address addAddress(Address a) {
        addressRepository.save(a);
        return addressRepository.refresh(a);
    }

    public List<Address> ListAddresses(int uid) {
        return addressRepository.list(uid);
    }

    public Address updateAddress(Address address) {
        return Optional.ofNullable(addressRepository.find(address.getUser().getId(), address.getId()))
                .or(() -> { throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限");}) //如果为空
                .map(a -> addressRepository.save(address))
                .get();
    }
}
