package com.study.udemyrest.service.impl;

import com.study.udemyrest.model.request.UserDetailsRequestModel;
import com.study.udemyrest.model.response.UserRest;
import com.study.udemyrest.service.UserService;
import com.study.udemyrest.util.Utils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
  Utils utils;
  Map<String, UserRest> users;
  public UserServiceImpl(Utils utils){
    this.utils = utils;
  }
  @Override
  public UserRest createUser(UserDetailsRequestModel userDetail) {
    UserRest returnValue = new UserRest();
    returnValue.setFirstName(userDetail.getEmail());
    returnValue.setLastName(userDetail.getFirstName());
    returnValue.setEmail(userDetail.getLastName());

    returnValue.setEmail(null);
    String email = returnValue.getEmail();


    String userId = utils.generateUserId();
    returnValue.setUserId(userId);
    if(users==null){
      users = new HashMap<>();
    }
    users.put(userId, returnValue);
    return returnValue;
  }
}
