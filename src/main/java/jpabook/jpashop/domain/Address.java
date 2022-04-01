package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable // JPA의 내장 타입, 값 타임 중 임베디드 타입(사용자 정의 타입)
@Getter // 값타입은 Setter를 제공하지 말자, 불변 객체로 만들어야 함.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 쓸 때는 기본 생성자를 만들어주어야함. -> 리플렉션같은 기술을 지원하려면?? -> 추후 알아보자.
    // public은 좀 그런데.. -> JPA에서 protected까지 허용해줌
    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}