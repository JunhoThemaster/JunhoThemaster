package project.ticketlink.model.member;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;


@Getter
@Setter
public class MemberRequest {
    private String email;
    private String pw;
    private String id;
    private String name;
    private BigInteger tel;
    private int age;


}
