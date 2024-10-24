package project.ticketlink.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public static String encodePassword(String pw) {
        return encoder.encode(pw);
    }

}
