package com.karakata.authserver.appuser.service;



import com.karakata.authserver.appuser.exception.UserNotFoundException;
import com.karakata.authserver.appuser.model.User;
import com.karakata.authserver.appuser.repository.UserRepository;
import com.karakata.authserver.roles.exception.RoleNotFoundException;
import com.karakata.authserver.roles.model.Role;
import com.karakata.authserver.roles.repository.RoleRepository;
import com.karakata.authserver.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addUser(User user) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new UserNotFoundException("Not a matching password");
        }
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String searchKey, String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role " + roleName + " not found"));
        User user = userRepository.findByUsernameOrEmailOrMobile(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new UserNotFoundException("User " + searchKey + " not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User fetchUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User " + id + " not found"));
    }

    @Override
    public User fetchUserByUsernameOrEmailOrMobile(String searchKey) {
        return userRepository.findByUsernameOrEmailOrMobile(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new UserNotFoundException("User " + searchKey + " not found"));
    }

    @Override
    public List<User> fetchByUserType(UserType userType, int pageNumber, int pageSize) {
        return userRepository.findByUserType(userType, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<User> fetchUsers(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }

    @Override
    public User editUser(User user, Long id) {
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User ID " + id + " does not exist"));
        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            savedUser.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getMobile()) && !"".equalsIgnoreCase(user.getMobile())) {
            savedUser.setMobile(user.getMobile());
        }
        if (Objects.nonNull(user.getAddress()) && !"".equals(user.getAddress())) {
            savedUser.setAddress(user.getAddress());
        }if (Objects.nonNull(user.getUserType()) && !"".equals(user.getUserType())){
            savedUser.setUserType(user.getUserType());
        }
        return userRepository.save(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else throw new UserNotFoundException("User " + id + " not found");
    }
}
