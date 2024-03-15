package com.karakata.authserver.appuser.service;






import com.karakata.authserver.appuser.model.User;
import com.karakata.authserver.staticdata.UserType;

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
