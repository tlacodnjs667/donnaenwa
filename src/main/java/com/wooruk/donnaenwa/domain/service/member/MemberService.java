package com.wooruk.donnaenwa.domain.service.member;

import com.wooruk.donnaenwa.controller.JoinRequestStatus;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.dto.member.JoinRequest;
import com.wooruk.donnaenwa.dto.member.LoginRequest;

public interface MemberService {

  JoinRequestStatus join (JoinRequest joinRequest);
  Member login (LoginRequest loginRequest);
}
