package com.example.chatapplication.service;

import com.example.chatapplication.dao.StatusRepository;
import com.example.chatapplication.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    public int saveStatus(Status status) {
        Status statusObj = statusRepository.save(status);
        return statusObj.getStatusId();
    }
}
