package com.example.chatapplication.dao;

import com.example.chatapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "Select * from tbl_user where username =:username and status_id = 1",nativeQuery = true)
    public List<User> findByUsername(String username);
}
