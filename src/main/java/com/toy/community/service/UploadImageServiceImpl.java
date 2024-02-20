package com.toy.community.service;

import com.toy.community.domain.entity.UploadImage;
import com.toy.community.repository.BoardRepository;
import com.toy.community.repository.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    private final UploadImageRepository uploadImageRepository;
    private  final BoardRepository boardRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String fileDir = rootPath + "/src/main/resources/static/upload-images/";


    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public UploadImage saveImage(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        //파일 저장
        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(UploadImage.builder()
                .originalFilename(originalFilename)
                .savedFilename(savedFilename)
                .build());

    }

    @Override
    public void deleteImage(UploadImage uploadImage) {

    }

    @Override
    public ResponseEntity<UrlResource> downloadImage(Long boardId) {
        return null;
    }

    // 파일 이름에서 확장자 추출하는 메서드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
