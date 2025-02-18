package com.wooruk.donnaenwa.domain.service;

import com.wooruk.donnaenwa.controller.JoinRequestStatus;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.dto.JoinRequest;
import com.wooruk.donnaenwa.dto.LoginRequestDto;

public interface MemberService {

  JoinRequestStatus join (JoinRequest joinRequest);
  Member login (LoginRequestDto loginRequest);
}
