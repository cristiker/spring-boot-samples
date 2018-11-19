package com.cristik.boot.application.jpa.service.impl;

import com.cristik.boot.application.jpa.dao.UserRepository;
import com.cristik.boot.application.jpa.entity.User;
import com.cristik.boot.application.jpa.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cristik
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean deleteByExample(User user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateUser(User user) {
        return true;
    }

    @Override
    public List<User> findByUser(User user) {
        return userRepository.findAll(Example.of(user));
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Page<User> findUserByPage(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findAll(pageable);
    }
}
