package com.itbank.worldcup.service;

import com.itbank.worldcup.component.FileComponent;
import com.itbank.worldcup.mapper.FileMapper;
import com.itbank.worldcup.model.Category;
import com.itbank.worldcup.model.FileEntity;
import com.itbank.worldcup.model.Gender;
import com.itbank.worldcup.model.Items;
import com.itbank.worldcup.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FileComponent fileComponent;
    private final FileMapper fileMapper;
    public List<Items> getList(int id) {
        return itemRepository.findAllById(id);
    }

    @Transactional
    public int insert(String name, MultipartFile file, String gender, Category category) throws IOException {
        FileEntity fileEntity = fileComponent.getSaveItemsImage(file);
        int row = fileMapper.insertFile(fileEntity);
        Items item = new Items();
        item.setCategory(category);  // 카테고리 설정
        item.setName(name);  // 아이템 이름 설정
        item.setGender(Gender.valueOf(gender));  // 성별 설정 (enum 처리)
        item.setFile(fileEntity);  // 파일 설정
        item.setWiDate(new Date());  // 등록일 설정
        itemRepository.save(item);
        return row;
    }
}
