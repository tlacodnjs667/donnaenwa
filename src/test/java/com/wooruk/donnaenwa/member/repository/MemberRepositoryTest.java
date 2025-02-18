package com.wooruk.donnaenwa.member.repository;

import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Application Context 사용하기 위해 해당 Test Annotation 사용
public class MemberRepositoryTest {
  @Autowired private MemberRepository memberRepository;

  @Test
  public void crudTest() {
    Member member = Member.builder().
        membername("심채원").
        password("ftislane").
        phoneNumber("010-2929-3928").
        build();

    memberRepository.save(member);


    Member foundMember = memberRepository.findById(1L).get();
  }
}
