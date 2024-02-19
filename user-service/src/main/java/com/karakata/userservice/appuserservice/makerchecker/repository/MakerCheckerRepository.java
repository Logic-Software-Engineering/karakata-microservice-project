package com.karakata.userservice.appuserservice.makerchecker.repository;

import com.karakata.userservice.appuserservice.makerchecker.model.MakerChecker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakerCheckerRepository extends JpaRepository<MakerChecker, String>, MakerCheckerRepositoryCustom {
}
