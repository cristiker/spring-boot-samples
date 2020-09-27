package com.cristik.boot.application.entity;

/**
 * @author cristik
 */
public class User {

    private String userName;

    private Integer score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
