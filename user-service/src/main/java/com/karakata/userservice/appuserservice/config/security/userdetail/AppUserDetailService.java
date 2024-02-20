package com.karakata.userservice.appuserservice.config.security.userdetail;

import com.karakata.userservice.appuserservice.appuser.exception.UserNotFoundException;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String searchKey) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey)
                .orElseThrow(()->new UserNotFoundException("User with username or email or mobile "+searchKey+" not found"));
        return new AppUserDetail(user);
    }
}
