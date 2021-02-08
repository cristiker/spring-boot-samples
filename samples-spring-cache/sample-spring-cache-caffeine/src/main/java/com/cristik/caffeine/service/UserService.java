package com.cristik.caffeine.service;

/**
 * @author cristik
 */
public interface UserService {

    String getUserName(Integer userId);

    void updateUserName(Integer userId, String userName);
}