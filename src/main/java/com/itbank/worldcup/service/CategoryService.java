package com.itbank.worldcup.service;

import com.itbank.worldcup.component.FileComponent;
import com.itbank.worldcup.mapper.CategoryMapper;
import com.itbank.worldcup.mapper.FileMapper;
import com.itbank.worldcup.model.Category;
import com.itbank.worldcup.model.FileEntity;
import com.itbank.worldcup.repository.CategoryRepository;
import com.itbank.worldcup.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileComponent fileComponent;
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final CategoryMapper categoryMapper;

    public List<Category> getList() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "wcDate"));
    }

    public List<Category> searchCategories(String search) {
        return categoryRepository.findBycNameContainingIgnoreCase(search);  // cName을 기준으로 검색
    }

    public boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category != null && category.getFile() != null) {
                // 파일 삭제
                String storedFileName = category.getFile().getStoredFileName();
                boolean isCategory = true; // 카테고리 파일인지 여부
                fileComponent.deleteFile(storedFileName, isCategory);
            }
            categoryRepository.deleteById(id);  // 삭제
            return true;
        }
        return false;  // 카테고리가 존재하지 않으면 false 반환
    }

    @Transactional // 트랜잭션 처리
    public int insert(String cName, String description, MultipartFile file) throws IOException {
        FileEntity fileEntity =  fileComponent.getSaveCategoryImage(file);
        fileEntity = fileRepository.save(fileEntity);
        if (!fileRepository.existsById(fileEntity.getId())) {
            return 0;
        }
        Category category = new Category();
        category.setCName(cName);
        category.setFile(fileEntity);
        category.setDescription(description);
        category.setUcDate(null);
        categoryRepository.save(category);
        if (!categoryRepository.existsById(category.getId())) {
            return 0;
        }
        return 1;
    }

    @Transactional
    public int getUpdate(int id, String cName, String description, MultipartFile file) throws IOException {
        boolean isCategory = true;
        Category category = getCategoryById(id);
        int fileUpdateCount = 0;
        if (file != null && !file.isEmpty()) {
            FileEntity existFile = category.getFile();
            String storedFileName = existFile.getStoredFileName();
            log.info(storedFileName);
            FileEntity newFile = fileComponent.getSaveCategoryImage(file);
            newFile.setId(existFile.getId()); // 기존 파일 ID 유지
            fileUpdateCount = fileMapper.updateFile(newFile);
            log.info(String.valueOf(newFile));
            log.info(String.valueOf(fileUpdateCount));
            if (existFile != null) {
                fileComponent.deleteFile(storedFileName,isCategory);
            }

        }
        int row = categoryMapper.update(id, cName, description);
        return row+fileUpdateCount;
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).get();
    }
}
