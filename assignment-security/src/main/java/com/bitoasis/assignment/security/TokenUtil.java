package com.bitoasis.assignment.security;

import com.bitoasis.assignment.util.Decoding;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    public String getEmailFromToken(String token) {
        String decode = Decoding.decode(token);
        return decode.split(":")[0];
    }

    public String getPasswordFromToken(String token) {
        String decode = Decoding.decode(token);
        return decode.split(":")[1];
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String email = getEmailFromToken(token);
        String password = getPasswordFromToken(token);
        return email.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword());
    }
}
