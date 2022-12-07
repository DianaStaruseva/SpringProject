package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Employee;
import com.example.demo.models.Post;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public String main(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "category/main";
    }

    @GetMapping("/category/add")
    public String add(Category category, Model model)
    {
        return "category/add";
    }

    @PostMapping("/category/add")
    public String add(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @GetMapping("/category/filter")
    public String categoryFilter(Model model)
    {
        return "category/filter";
    }

    @PostMapping("/category/filter/result")
    public String categoryResult(@RequestParam String name, Model model)
    {
        List<Category> result = categoryRepository.findByNameContains(name);
        model.addAttribute("result", result);
        return "category/filter";
    }

    @GetMapping("/category/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);
        ArrayList<Category> res = new ArrayList<>();
        category.ifPresent(res::add);
        model.addAttribute("categories", res);
        if (!categoryRepository.existsById(id)) {
            return "redirect:/category";
        }
        return "category/details";
    }

    @GetMapping("/category/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category",category);
        if (!categoryRepository.existsById(id)) {
            return "redirect:/category";
        }
        return "category/edit";
    }

    @PostMapping("/category/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("category")
    @Valid Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "category/edit";
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @PostMapping("/category/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(category);
        return "redirect:/category";
    }
}
