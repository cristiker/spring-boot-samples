package com.cristik.springcache.service;

/**
 * @author cristik
 */
public interface IUserService {

    String getUserName(Integer userId);

    void updateUserName(Integer userId, String userName);
}