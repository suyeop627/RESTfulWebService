package com.study.udemyrest.controller;

import com.study.udemyrest.exceptions.UserServiceException;
import com.study.udemyrest.model.request.UpdateUserDetailsRequestModel;
import com.study.udemyrest.model.request.UserDetailsRequestModel;
import com.study.udemyrest.model.response.UserRest;
import com.study.udemyrest.service.UserService;
import com.study.udemyrest.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")//http://localhost:8080/users
public class UserController {
  Map<String, UserRest> users;

  @Autowired
  UserService userService;

  @GetMapping
  public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "limit", defaultValue = "50") int limit,
                         @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort) {

    /*
    * @RequestParam
    * defalutValue 속성으로, 기본값 설정 가능. query string으로 전달받지 못한 경우, 해당 값을 defalutvalue로 처리할 수 있음
    * required 속성으로, 해당 parameter의 값을 필수적으로 넘기지 않아도 되도록 설정 가능.
      전달받지 못한 경우, null값으로 처리되므로, 기본형 parameter인 경우 에러 발생할 수 있음
    * */
    return "get users was called with page = " + page + " and limit = " + limit + " and sort = " + sort;
  }

  //객체를 리턴할때, 응답 미디어타입의 기본값은 json이지만, produces 속성으로, 반환할 타입을 복수지정 할 수 있음
  //클라이언트의 요청 헤더의 Accept 값에 따라, 해당하는 타입으로 리턴함
  //jackson-dataformat-xml = xml로 변환해줄 라이브러리
  //@GetMapping(path = "/{userId}", produces ={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
  public UserRest getUser2(@PathVariable String userId) {
    UserRest returnValue = new UserRest();
    returnValue.setFirstName("kim");
    returnValue.setLastName("sy");
    returnValue.setEmail("email@email.com");
    return returnValue;
  }

  //responseEnitty 클래스 사용하면 http status code를 포함하여 리턴할 수 있음, 헤더나 바디도 생성자에 넣어서 response entity만들 수도 있음
  @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
    if(users.containsKey(userId)){
      return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  //consumes : 어떤 미디어타입을 기반으로 요청을 처리하는지 구체적으로 저정함(json 또는 xml로 들어오는거 처리함)
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
              produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  //spring-boot-starter-validation 의존성 추가해야 @Valid 어노테이션 사용 가능함 - 객체안에서 검증 기능 제공
  public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetail) {

    UserRest returnValue = userService.createUser(userDetail);
    return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
  }

  @PutMapping(value="/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetail) {
    UserRest storedUserDetails = users.get(userId);
    storedUserDetails.setFirstName(userDetail.getFirstName());
    storedUserDetails.setLastName(userDetail.getLastName());
    users.put(userId, storedUserDetails);
    return storedUserDetails;
  }

  @DeleteMapping(value="/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    users.remove(id);
    //ResponseEntity는 불변객체로, 메서드페인으로 작성한 것들을 다 고려하고 build()메서드로 마지막에 객체를 생성함
    return ResponseEntity.noContent().build();
  }
}
