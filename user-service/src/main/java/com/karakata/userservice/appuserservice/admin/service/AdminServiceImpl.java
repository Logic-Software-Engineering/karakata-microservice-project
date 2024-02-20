package com.karakata.userservice.appuserservice.admin.service;


import com.karakata.userservice.appuserservice.address.exception.AddressNotFoundException;
import com.karakata.userservice.appuserservice.admin.exception.AdminNotFoundException;
import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.admin.repository.AdminRepository;
import com.karakata.userservice.appuserservice.appuser.exception.UserNotFoundException;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import com.karakata.userservice.appuserservice.roles.exception.RoleNotFoundException;
import com.karakata.userservice.appuserservice.roles.model.Role;
import com.karakata.userservice.appuserservice.roles.repository.RoleRepository;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin addAdmin(Admin admin) {
        if (!validatePhoneNumber(admin)) {
            throw new AdminNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }

        if (!admin.getUser().getPassword().equals(admin.getUser().getConfirmPassword())) {
            throw new AdminNotFoundException("Passwords do not match");
        }

        Role role = roleRepository.findByRoleName("Admin")
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        admin.getUser().setRoles(roles);
        admin.getUser().setUserType(UserType.ADMINISTRATOR);
        admin.getUser().setIsEnabled(true);
        admin.getUser().setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin fetchAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin ID " + id + " not found"));
    }

    @Override
    public Admin fetchAdminByUsernameOrEmailOrMobile(String searchKey) {
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new UserNotFoundException("User " + searchKey + " not found"));
        Admin admin = adminRepository.findByUser(user);
        return admin;
    }

    @Override
    public List<Admin> fetchAllAdminOrByName(String searchKey, int pageNumber, int pageSize) {
        if (searchKey.equals("")) {
            return adminRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
        } else return adminRepository.findByName(searchKey, searchKey, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Admin editAdmin(Admin admin, Long id) {
        Admin saveAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Admin ID " + id + " does not exist"));
        if (Objects.nonNull(admin.getFirstName()) && !"".equalsIgnoreCase(admin.getFirstName())) {
            saveAdmin.setFirstName(admin.getFirstName());
        }
        if (Objects.nonNull(admin.getLastName()) && !"".equalsIgnoreCase(admin.getLastName())) {
            saveAdmin.setLastName(admin.getLastName());
        }
        if (Objects.nonNull(admin.getUser()) && !"".equals(admin.getUser())) {
            saveAdmin.setUser(admin.getUser());
        }
        return adminRepository.save(saveAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        }
    }


    private static boolean validatePhoneNumber(Admin admin) {
        // validate phone numbers of format "1234567890"
        if (admin.getUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (admin.getUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (admin.getUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (admin.getUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (admin.getUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (admin.getUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (admin.getUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (admin.getUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
