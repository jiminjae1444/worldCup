package com.itbank.worldcup.controller;

import com.itbank.worldcup.model.Category;
import com.itbank.worldcup.model.Items;
import com.itbank.worldcup.service.CategoryService;
import com.itbank.worldcup.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    private final ItemService itemService;

    @PostMapping("/search")
    public HashMap<String,Object> search(@RequestBody String search){
        HashMap<String, Object> response = new HashMap<>();
        // 검색어가 비어있는지 체크
        if (search == null || search.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "검색어를 입력해주세요.");
            return response;
        }
        List<Category> categories = categoryService.searchCategories(search);
        if (categories != null && !categories.isEmpty()) {
            response.put("success", true);
            response.put("categories", categories);
        } else {
            response.put("success", false);
            response.put("message", "해당 월드컵은 존재하지 않습니다.");
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,Object> deleteCategory(@PathVariable int id) {
        HashMap<String,Object> response = new HashMap<>();

        boolean deleted = categoryService.deleteCategory(id);  // 카테고리 삭제 처리
        if (deleted) {
            response.put("success", true);
            response.put("message", "카테고리가 삭제되었습니다.");
        } else {
            response.put("success", false);
            response.put("message", "카테고리를 찾을 수 없습니다.");
        }
        return response;
    }

    @PostMapping("/add")
    public HashMap<String,Object> addCategory(
            @RequestParam("cName") String cName,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String,Object> response = new HashMap<>();
        int row = categoryService.insert(cName,description,file);
        if (row > 0) {
            response.put("success", true);
            response.put("message","카테고리가 성공적으로 추가되었습니다.");
        }else{
            response.put("success", false);
            response.put("message","카테고리 추가에 실패했습니다.");
        }
        return response;
    }

    // 카테고리 수정 및 파일 처리
    @PostMapping("/edit/{id}")
    public HashMap<String,Object> editCategory(
            @PathVariable int id,
            @RequestParam String cName,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file) throws IOException {
            // 카테고리 조회
           int row = categoryService.getUpdate(id,cName,description,file);
           HashMap<String,Object> response = new HashMap<>();
           if (row >= 1) {
               response.put("success", true);
               response.put("message","카테고리가 성공적으로 수정되었습니다.");
           }else{
               response.put("success", false);
               response.put("message","카테고리 수정에 실패했습니다.");
           }
           return response;
    }

    @GetMapping("/{id}/Items")
    public HashMap<String,Object> getCategoryItems(@PathVariable int id){
        HashMap<String,Object> response = new HashMap<>();
        List<Items> itemList = itemService.getList(id);
        if (itemList != null && !itemList.isEmpty()) {
            if(itemList.size()>=16){
                response.put("success", true);
                response.put("items", itemList);
            }
            response.put("success", true);
            response.put("message","아이템 수가 모자랍니다");
            response.put("items",itemList);
        }else {
            response.put("success", false);
            response.put("message","아이템이 없습니다.");
        }
        return response;
    }

    @PostMapping("/items/add/{id}")
    public HashMap<String, Object> addItem(@PathVariable int id,
                                           @RequestParam String name,
                                           @RequestParam MultipartFile file,
                                           @RequestParam String gender) throws IOException {
        HashMap<String,Object> response = new HashMap<>();
        Category category = categoryService.getCategoryById(id);
        int row = itemService.insert(name,file,gender,category);
        if (row>0){
            response.put("success",true);
            response.put("message", "아이템이 성공적으로 추가되었습니다.");
        }else{
            response.put("success", false);
            response.put("message", "아이템 추가에 실패했습니다.");
        }
        return response;
    }
}
