package com.example.chatapplication.dao;

import com.example.chatapplication.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
