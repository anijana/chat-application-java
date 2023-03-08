package com.example.chatapplication.controller;

import com.example.chatapplication.dao.StatusRepository;
import com.example.chatapplication.dao.UserRepository;
import com.example.chatapplication.model.Status;
import com.example.chatapplication.model.User;
import com.example.chatapplication.service.UserService;
import com.example.chatapplication.utils.CommonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @PostMapping(value = "/create-user")
    public ResponseEntity<String> createUser(@RequestBody String userData){
        JSONObject isRequestValid = validateUserRequest(userData);

        User user = null;

        if(isRequestValid.isEmpty()){
            user = setUser(userData);
            userService.saveUser(user);
        }else {
            return new ResponseEntity<String>(isRequestValid.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Saved",HttpStatus.CREATED);
    }

    private JSONObject validateUserRequest(String userData) {
        JSONObject userJson = new JSONObject(userData);
        JSONObject errorList = new JSONObject();

        if(userJson.has("username")){
            String username = userJson.getString("username");
            List<User> userList = userRepository.findByUsername(username);
            if(userList.size() > 0){
                errorList.put("username","username already exists");
                return errorList;
            }
        }else {
            errorList.put("username","Missing parameter");
        }
        if(userJson.has("password")){
            String password = userJson.getString("password");
            if(!CommonUtils.isValidPassword(password)){
                errorList.put("password","Please Enter valid password eg: anijana@123");
            }
        }else {
            errorList.put("password","Missing parameter");
        }
        if(userJson.has("firstName")){
            String firstName = userJson.getString("firstName");
        }else {
            errorList.put("firstName","Missing parameter");
        }
        if(userJson.has("email")){
            String email = userJson.getString("email");
            if(!CommonUtils.isValidEmail(email)){
                errorList.put("email","Please enter valid email eg: Ani123@gamil.com");
            }
        }else {
            errorList.put("email","Missing parameter");
        }
        if(userJson.has("phoneNumber")){
            String phoneNumber = userJson.getString("phoneNumber");
            if(!CommonUtils.isValidPhoneNumber(phoneNumber)){
                errorList.put("phoneNumber","Please enter valid phoneNumber ");
            }
        }else {
            errorList.put("phoneNumber","Missing parameter");
        }

        return errorList;

    }

    private User setUser(String userData) {
        User user = new User();
        JSONObject jsonObject = new JSONObject(userData);

        user.setEmail(jsonObject.getString("email"));
        user.setPassword(jsonObject.getString("password"));
        user.setFirstName(jsonObject.getString("firstName"));
        user.setUsername(jsonObject.getString("username"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));

        if(jsonObject.has("age")){
            user.setAge(jsonObject.getInt("age"));
        }
        if(jsonObject.has("lastName")){
            user.setLastName(jsonObject.getString("lastName"));
        }
        if(jsonObject.has("gender")){
            user.setGender(jsonObject.getString("gender"));
        }

        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        user.setCreatedDate(createTime);

        Status status = statusRepository.findById(1).get();
        user.setStatusId(status);

        return user;
    }
}
