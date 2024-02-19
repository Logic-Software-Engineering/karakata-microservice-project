package com.karakata.userservice.appuserservice.admin.service;



import com.karakata.userservice.appuserservice.admin.model.Admin;

import java.util.List;

public interface AdminService {
    Admin addAdmin(Admin admin);
    Admin fetchAdminById(Long id);
    Admin fetchAdminByUsernameOrEmailOrMobile(String searchKey);
    List<Admin> fetchAllAdminOrByName(String searchKey, int pageNumber, int pageSize);
    Admin editAdmin(Admin admin, Long id);
    void deleteAdmin(Long id);
}
