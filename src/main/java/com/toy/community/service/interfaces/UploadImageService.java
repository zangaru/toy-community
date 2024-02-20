package com.toy.community.service.interfaces;

import com.toy.community.domain.entity.UploadImage;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageService {
    UploadImage saveImage(MultipartFile multipartFile) throws IOException;
    void deleteImage(UploadImage uploadImage) throws IOException;
    ResponseEntity<UrlResource> downloadImage(Long boardId);
}
