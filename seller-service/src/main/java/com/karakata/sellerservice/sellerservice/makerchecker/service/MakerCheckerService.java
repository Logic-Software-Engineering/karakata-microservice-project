package com.karakata.sellerservice.sellerservice.makerchecker.service;

import com.karakata.sellerservice.sellerservice.makerchecker.model.MakerChecker;

import java.util.List;

public interface MakerCheckerService {
    MakerChecker fetchMakerCheckerById(String id);
    MakerChecker fetchByEntityId(Long entityId);
    List<MakerChecker> fetchByCheckerId(Long checkerId, int pageNumber, int pageSize);
    List<MakerChecker> fetchAllMakerCheckers(int pageNumber, int pageSize);
}
