package com.cristik.boot.samples.jpa.service;

import com.cristik.boot.samples.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author cristik
 */
public interface IUserService {

    User insert(User user);

    Boolean deleteByExample(User user);

    Boolean deleteById(Integer id);

    Boolean updateUser(User user);

    List<User> findByUser(User user);

    User findUserById(Integer id);

    Page<User> findUserByPage(Integer page,Integer pageSize);

}
