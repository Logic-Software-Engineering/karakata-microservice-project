package com.karakata.authserver.config.userdetail.authprovider;

import com.karakata.authserver.config.userdetail.userdetails.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AppUserDetailService appUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String searchKey= authentication.getName();
        String password= authentication.getCredentials().toString();
        UserDetails user= appUserDetailService.loadUserByUsername(searchKey);
        return checkPassword(user, password);
    }

    private Authentication checkPassword(UserDetails user, String password) {
        if (passwordEncoder.matches(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    user.getAuthorities());
        }else{
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
