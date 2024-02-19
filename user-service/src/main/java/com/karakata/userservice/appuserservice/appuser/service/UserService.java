package com.karakata.userservice.appuserservice.appuser.service;




import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.staticdata.UserType;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void addRoleToUser(String searchKey, String roleName);
    User fetchUserById(Long id);
    User fetchUserByUsernameOrEmailOrMobile(String searchKey);
    List<User> fetchByUserType(UserType userType, int pageNumber, int pageSize);
    List<User> fetchUsers(int pageNumber, int pageSize);
    User editUser(User user, Long id);
    void deleteUser(Long id);
}
