package com.karakata.userservice.appuserservice.admin.controller;


import com.karakata.userservice.appuserservice.admin.dto.AdminRequest;
import com.karakata.userservice.appuserservice.admin.dto.AdminResponse;
import com.karakata.userservice.appuserservice.admin.dto.AdminUpdate;
import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.admin.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/admin")
public record AdminController(AdminService adminService, ModelMapper modelMapper,
                              ApplicationEventPublisher publisher) {

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody AdminRequest adminRequest, HttpServletRequest request) {
        Admin admin = modelMapper.map(adminRequest, Admin.class);
        Admin post = adminService.addAdmin(admin);
//        publisher.publishEvent(new RegistrationEvent(post.getUser(), applicationUrl(request)));
        AdminRequest posted = modelMapper.map(post, AdminRequest.class);
        return new ResponseEntity<>("Admin is successfully added", HttpStatus.CREATED);
    }

    @GetMapping("findAdminById")
    public ResponseEntity<AdminResponse> getAdminById(@RequestParam("id") Long id){
        Admin admin=adminService.fetchAdminById(id);
        AdminResponse adminResponse=convertAdminToDto(admin);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @GetMapping("/adminByUsernameOrEmailOrMobile")
    public ResponseEntity<AdminResponse> getAdminByUsernameOrEmailOrMobile(@RequestParam("searchKey") String searchKey) {
        Admin admin = adminService.fetchAdminByUsernameOrEmailOrMobile(searchKey);
        AdminResponse adminResponse = convertAdminToDto(admin);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @GetMapping("/allAdminOrByName")
    public ResponseEntity<List<AdminResponse>> getAllAdminOrByName(@RequestParam(value = "searchKey",required = false,
                                                                     defaultValue = "") String searchKey,
                                                                   @RequestParam("pageNumber") int pageNumber,
                                                                   @RequestParam("pageSize") int pageSize) {

        return new ResponseEntity<>(adminService.fetchAllAdminOrByName(searchKey, pageNumber, pageSize)
                .stream().map(this::convertAdminToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/editAdmin")
    public ResponseEntity<String> editAdmin(@RequestParam("id") Long id, @RequestBody AdminUpdate adminUpdate) {
        Admin admin = modelMapper.map(adminUpdate, Admin.class);
        Admin post = adminService.editAdmin(admin, id);
        AdminUpdate posted = modelMapper.map(post, AdminUpdate.class);
        return new ResponseEntity<>("Recorded is updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<String> deleteAdmin(Long id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>("Record is deleted successfully", HttpStatus.OK);
    }

    private AdminResponse convertAdminToDto(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setId(admin.getId());
        adminResponse.setFirstName(admin.getFirstName());
        adminResponse.setLastName(admin.getLastName());
        adminResponse.setEmail(admin.getUser().getEmail());
        adminResponse.setMobile(admin.getUser().getMobile());
        adminResponse.setFullAddress(admin.getUser().getAddress().getFullAddress());
        adminResponse.setLandmark(admin.getUser().getAddress().getLandmark());
        adminResponse.setCity(admin.getUser().getAddress().getCity());
        adminResponse.setState(admin.getUser().getAddress().getState());
        adminResponse.setCountry(admin.getUser().getAddress().getCountry());
        return adminResponse;
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
