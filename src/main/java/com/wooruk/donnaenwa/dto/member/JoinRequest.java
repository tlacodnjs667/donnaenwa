package com.wooruk.donnaenwa.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinRequest {
  @NotBlank(message = "USER_NAME_REQUIRED")
  private String name;
  @NotBlank(message = "PASSWORD_REQUIRED")
  private String password;
  @NotBlank(message = "EMAIL_REQUIRED")
  private String email;
  private String phoneNumber;
}
