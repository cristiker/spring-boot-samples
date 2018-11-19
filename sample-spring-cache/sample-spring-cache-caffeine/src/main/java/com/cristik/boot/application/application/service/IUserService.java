package com.cristik.boot.application.application.service;

/**
 * @author cristik
 */
public interface IUserService {

    String getUserName(Integer userId);

    void updateUserName(Integer userId, String userName);
}
