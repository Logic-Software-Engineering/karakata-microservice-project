package com.karakata.userservice.appuserservice.appuser.controller;


import com.karakata.userservice.appuserservice.appuser.dto.AddRoleToUser;
import com.karakata.userservice.appuserservice.appuser.dto.UserRequest;
import com.karakata.userservice.appuserservice.appuser.dto.UserResponse;
import com.karakata.userservice.appuserservice.appuser.dto.UserUpdate;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.service.UserService;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/user")
public record UserController(UserService userService, ModelMapper modelMapper) {

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        User post = userService.addUser(user);
        UserRequest posted = modelMapper.map(post, UserRequest.class);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/addRoleToUser")
    public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUser addRoleToUser){
        userService.addRoleToUser(addRoleToUser.getUsernameOrEmailOPassword(), addRoleToUser.getRoleName());
        return new ResponseEntity<>("Role has been added to user successfully",HttpStatus.CREATED);
    }

    @GetMapping("/findUserById")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Long id){
        User user = userService.fetchUserById(id);
        UserResponse userResponse=convertToDto(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/userByUsernameOrEmailOrMobile")
    public ResponseEntity<UserResponse> getUser(@RequestParam("searchKey") String searchKey){
        User user=userService.fetchUserByUsernameOrEmailOrMobile(searchKey);
        UserResponse userResponse=convertToDto(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/findByUserType")
    public ResponseEntity<List<UserResponse>> getUserByType(@RequestParam("searchKey") UserType userType,
                                                            @RequestParam("pageNumber") int pageNumber,
                                                            @RequestParam("pageSize") int pageSize){

        List<User> users=userService.fetchByUserType(userType, pageNumber, pageSize);
        return new ResponseEntity<>(users.stream().map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam("pageNumber") int pageNumber,
                                                  @RequestParam("pageSize") int pageSize) {
        List<User> users=userService.fetchUsers(pageNumber, pageSize);
        return new ResponseEntity<>(users.stream().map(this::convertToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<String> editUser(@RequestBody UserUpdate userUpdate, @RequestParam("id") Long id){
        User user=modelMapper.map(userUpdate,User.class);
        User post=userService.editUser(user,id);
        UserUpdate posted=modelMapper.map(post,UserUpdate.class);
        return new ResponseEntity<>("You have successfully updated your record ",HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User with ID "+id+" has been successfully deleted ",HttpStatus.OK);
    }




    private UserResponse convertToDto(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUserType(user.getUserType());
        response.setUsername(user.getUsername());
        response.setMobile(user.getMobile());
        response.setEmail(user.getEmail());
        response.setFullAddress(user.getAddress().getFullAddress());
        response.setLandmark(user.getAddress().getLandmark());
        response.setCity(user.getAddress().getCity());
        response.setState(user.getAddress().getState());
        response.setCountry(user.getAddress().getCountry());
        return response;
    }
}
