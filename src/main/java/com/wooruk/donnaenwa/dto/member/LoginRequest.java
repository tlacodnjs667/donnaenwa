package com.wooruk.donnaenwa.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginRequest {
  @NotBlank(message = "USER_NAME_REQUIRED")
  private String username;
  @NotBlank(message = "PASSWORD_REQUIRED")
  private String password;
}
