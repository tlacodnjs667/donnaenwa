package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.dto.JoinRequest;
import lombok.Getter;
import org.hibernate.mapping.Join;

@Getter
public enum JoinRequestStatus {
  USER_CREATED, USERNAME_EXISTED, EMAIL_EXISTED;
}
