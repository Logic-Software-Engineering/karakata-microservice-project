package com.karakata.userservice.appuserservice.makerchecker.repository;


import com.karakata.userservice.appuserservice.makerchecker.model.MakerChecker;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MakerCheckerRepositoryCustom {

    @Query("From MakerChecker m Where m.entityId=?1")
    Optional<MakerChecker> findByEntityId(Long entityId);

    @Query("From MakerChecker m Where m.adminId=?1")
    List<MakerChecker> findByCheckerId(Long adminId, PageRequest pageRequest);
}
