package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.FormRepository;
import com.example.demo.repo.ManufacturerRepository;
import com.example.demo.repo.ProductRepository;
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
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/product")
    public String main(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product/main";
    }

    @GetMapping("/product/add")
    public String add(Product product, Model model)
    {
        Iterable<Form> forms = formRepository.findAll();
        model.addAttribute("forms", forms);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Iterable<Manufacturer> manufacturers = manufacturerRepository.findAll();
        model.addAttribute("manufacturers", manufacturers);
        return "product/add";
    }

    @PostMapping("/product/add")
    public String postAdd(@ModelAttribute("product")  @Valid Product product, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {
            Iterable<Form> form = formRepository.findAll();
            model.addAttribute("form", form);
            Iterable<Category> category = categoryRepository.findAll();
            model.addAttribute("category", category);
            Iterable<Manufacturer> manufacturer = manufacturerRepository.findAll();
            model.addAttribute("manufacturer", manufacturer);
            return "product/add";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/product/filter")
    public String filter(Model model)
    {
        return "product/filter";
    }

    @GetMapping("/product/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        if (!productRepository.existsById(id)) {
            return "redirect:/product";
        }
        return "product/details";
    }

    @GetMapping("/product/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {

        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product",product);
        Iterable<Form> forms = formRepository.findAll();
        model.addAttribute("forms", forms);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Iterable<Manufacturer> manufacturers = manufacturerRepository.findAll();
        model.addAttribute("manufacturers", manufacturers);
        if (!productRepository.existsById(id)) {
            return "redirect:/product";
        }
        return "product/edit";
    }

    @PostMapping("/product/{id}/edit")
    public String postUpdate(@PathVariable("id") long id,@ModelAttribute ("product")
    @Valid Product product, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "product/edit";
        productRepository.save(product);
        return "redirect:/product";
    }

    @PostMapping("/product/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/product";
    }

}
