package org.example.jpql;

import lombok.Getter;

@Getter
public class MemberDto {

    // 생성자 반드시 필요
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private String username;

    private int age;

}
