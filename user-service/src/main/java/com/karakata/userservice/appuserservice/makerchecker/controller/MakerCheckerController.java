package com.karakata.userservice.appuserservice.makerchecker.controller;


import com.karakata.userservice.appuserservice.makerchecker.dto.MakerCheckerResponse;
import com.karakata.userservice.appuserservice.makerchecker.model.MakerChecker;
import com.karakata.userservice.appuserservice.makerchecker.service.MakerCheckerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/makerChecker")
public record MakerCheckerController(MakerCheckerService makerCheckerService) {
    @GetMapping("/findByMakerCheckerId")
    public ResponseEntity<MakerCheckerResponse> getMakerCheckerById(@RequestParam("id") String id) {

        MakerChecker makerChecker = makerCheckerService.fetchMakerCheckerById(id);
        MakerCheckerResponse makerCheckerResponse = convertMakerCheckerToDto(makerChecker);
        return new ResponseEntity<>(makerCheckerResponse, HttpStatus.OK);
    }

    @GetMapping("/findByEntityId")
    public ResponseEntity<MakerCheckerResponse> getByEntityId(@RequestParam("entityId") Long entityId) {

        MakerChecker makerChecker = makerCheckerService.fetchByEntityId(entityId);
        MakerCheckerResponse makerCheckerResponse = convertMakerCheckerToDto(makerChecker);
        return new ResponseEntity<>(makerCheckerResponse, HttpStatus.OK);
    }

    @GetMapping("/findByCheckerId")
    public ResponseEntity<List<MakerCheckerResponse>> getByCheckerId(@RequestParam("checkerId") Long checkerId,
                                                                     @RequestParam("pageNumber") int pageNumber,
                                                                     @RequestParam("pageSize") int pageSize) {

        return new ResponseEntity<>(makerCheckerService.fetchByCheckerId(checkerId,pageNumber,pageSize)
                .stream().map(this::convertMakerCheckerToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findAllMakerCheckers")
    public ResponseEntity<List<MakerCheckerResponse>> getAllMakerCheckers(@RequestParam("pageNumber") int pageNumber,
                                                                          @RequestParam("pageSize") int pageSize) {

        return new ResponseEntity<>(makerCheckerService.fetchAllMakerCheckers(pageNumber, pageSize)
                .stream().map(this::convertMakerCheckerToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    private MakerCheckerResponse convertMakerCheckerToDto(MakerChecker makerChecker) {
        MakerCheckerResponse makerCheckerResponse = new MakerCheckerResponse();
        makerCheckerResponse.setId(makerChecker.getId());
        makerCheckerResponse.setEntityType(makerChecker.getEntityType());
        makerCheckerResponse.setEntityId(makerChecker.getEntityId());
        makerCheckerResponse.setRequestType(makerChecker.getRequestType());
        makerCheckerResponse.setOldState(makerChecker.getOldState());
        makerCheckerResponse.setNewState(makerChecker.getNewState());
        makerCheckerResponse.setRequestStatus(makerChecker.getRequestStatus());
        makerCheckerResponse.setAdminId(makerChecker.getAdminId());
        makerCheckerResponse.setCreatedAt(makerChecker.getCreatedAt());
        makerCheckerResponse.setUpdatedAt(makerChecker.getUpdatedAt());
        return makerCheckerResponse;
    }
}
