package com.wooruk.donnaenwa.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginRequestDto {
  @NotBlank(message = "USER_NAME_REQUIRED")
  String username;
  @NotBlank(message = "PASSWORD_REQUIRED")
  String password;
}
