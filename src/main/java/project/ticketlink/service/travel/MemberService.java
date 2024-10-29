package project.ticketlink.service.travel;


import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.ticketlink.Util.JwtUtil;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.repository.MemberRepository;

import java.math.BigInteger;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;


    @Autowired
    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }






    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean chekPw(String pw,String encodedPw){
        return passwordEncoder.matches(pw,encodedPw);
    }


    @Transactional
    public boolean joinmember(String id, String pw, String name, BigInteger tel,String email,int age,String memType) {


        if (memberRepository.existsByMemId(id)){
            return false;
        }

    try {
        Member member = new Member();
        member.setMemId(id);

        member.setMemAge(age);

        member.setMemName(name);



        if (pw != null) {
            member.setMemPw(passwordEncoder.encode(pw));
        }
        member.setMemTel(tel);

        member.setMemMail(email);

        member.setMemType(memType);



        if (memberRepository.count() == 0) {
            member.setMemisAdmin(true); // 첫 회원에게 관리자 권한 부여
        }
        memberRepository.save(member);
        return true; // 성공


    }catch (Exception e){
        e.printStackTrace();
        return false;
    }

    }

    public Member login(String Id , String Pw) {
        Member member = memberRepository.findByMemId(Id);


        if (member != null) {
            if (passwordEncoder.matches(Pw, member.getMemPw())) { // 패스워드 비교
                return member;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Member not found");
        }


    }

    public String getMemberinfo(String Id) {
        return memberRepository.getEmailById(Id);
    }

    public String genToken(Member member){
        return jwtUtil.generateToken(member.getMemId());

    }
    public String refreshToken(Member member) {
        return jwtUtil.generateRefreshToken(member.getMemId());
    }

    public Member getmemberById(String Id) {
        return memberRepository.findByMemId(Id);
    }







}
