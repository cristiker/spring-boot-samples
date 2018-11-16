package com.cristik.boot.samples.jpa.dao;

import com.cristik.boot.samples.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cristik
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
