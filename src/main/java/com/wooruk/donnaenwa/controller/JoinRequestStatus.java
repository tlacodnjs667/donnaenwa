package com.wooruk.donnaenwa.controller;

import lombok.Getter;

@Getter
public enum JoinRequestStatus {
  USER_CREATED, USERNAME_EXISTED, EMAIL_EXISTED;
}
