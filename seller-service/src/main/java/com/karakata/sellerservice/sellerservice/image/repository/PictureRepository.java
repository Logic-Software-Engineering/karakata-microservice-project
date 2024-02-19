package com.karakata.sellerservice.sellerservice.image.repository;

import com.karakata.sellerservice.sellerservice.image.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
