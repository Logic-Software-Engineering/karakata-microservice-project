package com.karakata.sellerservice.sellerservice.makerchecker.repository;

import com.karakata.sellerservice.sellerservice.makerchecker.model.MakerChecker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakerCheckerRepository extends JpaRepository<MakerChecker, String>, MakerCheckerRepositoryCustom {
}
