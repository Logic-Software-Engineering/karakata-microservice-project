package com.karakata.authserver.appuser.repository;


import com.karakata.authserver.appuser.model.User;
import com.karakata.authserver.staticdata.UserType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    @Query("From User u Where u.username=?1 Or u.email=?2 Or u.mobile=?3")
    Optional<User> findByUsernameOrEmailOrMobile(String key1, String key2, String key3);

    @Query("From User u Where u.userType=?1")
    List<User> findByUserType(UserType userType, PageRequest pageRequest);
}
