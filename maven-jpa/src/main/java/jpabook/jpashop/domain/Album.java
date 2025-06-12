package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("A") //  DTYPE 컬럼에 저장될 값을 변경할 수 있다. 여기서는 "A" 값을 사용함
public class Album extends Item {

    private String artist;

}
