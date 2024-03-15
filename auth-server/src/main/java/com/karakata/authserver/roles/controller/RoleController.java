package com.karakata.authserver.roles.controller;




import com.karakata.authserver.roles.dto.RoleRequest;
import com.karakata.authserver.roles.dto.RoleResponse;
import com.karakata.authserver.roles.dto.RoleUpdate;
import com.karakata.authserver.roles.model.Role;
import com.karakata.authserver.roles.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/role")
public record RoleController(RoleService roleService, ModelMapper modelMapper) {

    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestBody RoleRequest roleRequest){
        Role role=modelMapper.map(roleRequest,Role.class);
        Role post=roleService.saveRole(role);
        RoleRequest posted=modelMapper.map(post, RoleRequest.class);
        return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/roleByName")
    public ResponseEntity<RoleResponse> getRoleByName(@RequestParam("roleName") String roleName){
        Role role=roleService.fetchRoleByName(roleName);
        RoleResponse roleResponse=convertToDto(role);
        return new ResponseEntity<>(roleResponse, HttpStatus.OK);
    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<RoleResponse>> getAllRoles(@RequestParam("pageNumber") int pageNumber,
                                                         @RequestParam("pageSize") int pageSize){
        List<RoleResponse> roleResponse = roleService.fetchAllRoles(pageNumber, pageSize)
                .stream().map(this::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(roleResponse,HttpStatus.OK);
    }

    @PutMapping("/editRole")
    public ResponseEntity<String> editRole(@RequestBody RoleUpdate roleUpdate,
                                           @RequestParam("roleName") String roleName){
        Role role=modelMapper.map(roleUpdate,Role.class);
        Role post=roleService.editRole(role,roleName);
        RoleUpdate posted =modelMapper.map(post,RoleUpdate.class);
        return new ResponseEntity<>("Role ID "+roleName+" updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<String> deleteRole(@RequestParam("id") Long id){
        roleService.deleteRole(id);
        return new ResponseEntity<>("Role Name "+id+" deleted successfully", HttpStatus.OK);
    }

    private RoleResponse convertToDto(Role role){
        RoleResponse roleResponse=new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setRoleName(role.getRoleName());
        return roleResponse;
    }
}
