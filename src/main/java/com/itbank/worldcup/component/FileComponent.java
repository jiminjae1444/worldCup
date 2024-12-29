package com.itbank.worldcup.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.itbank.worldcup.model.FileEntity;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileComponent {

    @Value("${file.upload.categoryDir}")
    private String saveCategoryDirectory = "C:\\upload\\worldcup\\categoryImg";
    @Value("${file.upload.itemsDir}")
    private String saveItemsDirectory = "C:\\upload\\worldcup\\items";

    @PostConstruct
    public void init() {
        createDirectoryIfNotExists(saveCategoryDirectory);
        createDirectoryIfNotExists(saveItemsDirectory);
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // 디렉토리가 없으면 생성
        }
    }

    public FileEntity getSaveCategoryImage(MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String storedFileName = System.currentTimeMillis() + extension;
        File dest = new File(saveCategoryDirectory, storedFileName);
        file.transferTo(dest);  // 파일을 지정된 경로에 저장

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalFileName(file.getOriginalFilename());
        fileEntity.setStoredFileName(storedFileName);
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setWfDate(new java.util.Date());  // 파일 업로드 시간 설정
        fileEntity.setUfDate(new java.util.Date());  //파일 수정 시간 설정
        return fileEntity;
    }

    // 아이템 이미지 저장 메소드 (카테고리와 다른 경로 사용)
    public FileEntity getSaveItemsImage(MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String storedFileName = System.currentTimeMillis() + extension;
        File dest = new File(saveItemsDirectory, storedFileName);
        file.transferTo(dest);  // 파일을 지정된 경로에 저장

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalFileName(file.getOriginalFilename());
        fileEntity.setStoredFileName(storedFileName);
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());
        fileEntity.setWfDate(new java.util.Date());  // 파일 업로드 시간 설정
        return fileEntity;
    }

    public void deleteFile(String storedFileName,boolean isCategory) {
        String directoryPath = isCategory ? saveCategoryDirectory : saveItemsDirectory;
        File deleteFile = new File(directoryPath,storedFileName);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
