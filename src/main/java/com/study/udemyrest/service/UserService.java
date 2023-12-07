package com.study.udemyrest.service;

import com.study.udemyrest.model.request.UserDetailsRequestModel;
import com.study.udemyrest.model.response.UserRest;

public interface UserService {
 UserRest createUser(UserDetailsRequestModel userDetails);


}
