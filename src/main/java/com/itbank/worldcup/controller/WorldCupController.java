package com.itbank.worldcup.controller;

import com.itbank.worldcup.model.Category;
import com.itbank.worldcup.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/worldcup")
@RequiredArgsConstructor
public class WorldCupController {
    private final CategoryService categoryService;
    @GetMapping("/list")
    public void list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        List<Category> categories = categoryService.getList();
        model.addAttribute("categories",categories);
        model.addAttribute("userRole",userRole);
    }

    @GetMapping("/category/add")
    public String categoryAdd() {
        return "worldcup/categoryAdd";
    }
    @GetMapping("/category/edit/{id}")
    public String categoryEdit(@PathVariable int id,Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category",category);
        return "worldcup/categoryEdit";
    }
}
