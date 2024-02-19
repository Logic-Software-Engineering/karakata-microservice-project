package com.karakata.userservice.appuserservice.admin.repository;


import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.appuser.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepositoryCustom {
    @Query("From Admin a Where a.firstName=?1 Or a.lastName=?2")
    List<Admin> findByName(String firstName, String lastName, PageRequest pageRequest);

    @Query("From Admin a Where a.user=?1")
    Admin findByUser(User user);
}
