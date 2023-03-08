package com.example.chatapplication.controller;

import com.example.chatapplication.model.Status;
import com.example.chatapplication.service.StatusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @PostMapping("/create-status")
    public ResponseEntity<String> createStatus(@RequestBody String statusData){
        Status status = setStatus(statusData);
        int statusId = statusService.saveStatus(status);
        return new ResponseEntity<String>("status save"+statusId, HttpStatus.CREATED);
    }

    private Status setStatus(String statusData) {
        Status status = new Status();

        JSONObject jsonObject = new JSONObject(statusData);
        String statusName = jsonObject.getString("statusName");
        String statusDescription = jsonObject.getString("statusDescription");

        status.setStatusName(statusName);
        status.setStatusDescription(statusDescription);

        return status;
    }
}
