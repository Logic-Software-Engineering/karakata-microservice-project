package com.karakata.authserver.config.userdetail.userdetails;


import com.karakata.authserver.appuser.exception.UserNotFoundException;
import com.karakata.authserver.appuser.model.User;
import com.karakata.authserver.appuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String searchKey) {
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey)
                .orElseThrow(()->new UserNotFoundException("User with username or email or mobile "+searchKey+" not found"));
        return new AppUserDetail(user);
    }
}
