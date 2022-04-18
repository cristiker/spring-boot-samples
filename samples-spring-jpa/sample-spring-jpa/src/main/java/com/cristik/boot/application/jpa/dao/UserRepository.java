package com.cristik.sample.log4j2.jpa.dao;

import com.cristik.sample.log4j2.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cristik
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
