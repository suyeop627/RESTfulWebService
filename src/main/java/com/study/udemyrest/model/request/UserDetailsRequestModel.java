package com.study.udemyrest.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDetailsRequestModel {
  @NotNull(message = "First name cannot be null")//NotNull로 설정한 필드의 값이 요청에 포함되어 있지 않으면 400 badrequest
  @Size(min=2, message="First name must not be less than 2 characters ")
  private String firstName;
  @NotNull(message = "Last name cannot be null")
  @Size(min=2, message="Last name must not be less than 2 characters ")
  private String lastName;
  @NotNull(message = "Email name cannot be null")
  @Email//이메일 형식 제한
  private String email;
  @NotNull(message = "Password cannot be null")
  @Size(min = 8, max=16, message = "Password must be equal or grater then 8 and less than 16 characters")//길이 제한
  private String password;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
