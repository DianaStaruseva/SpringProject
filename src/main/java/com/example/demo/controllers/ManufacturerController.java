package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Manufacturer;
import com.example.demo.repo.ManufacturerRepository;
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
public class ManufacturerController {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/manufacturer")
    public String main(Model model) {
        Iterable<Manufacturer> manufacturers = manufacturerRepository.findAll();
        model.addAttribute("manufacturers", manufacturers);
        return "manufacturer/main";
    }

    @GetMapping("/manufacturer/add")
    public String add(Manufacturer manufacturer, Model model)
    {
        return "manufacturer/add";
    }

    @PostMapping("/manufacturer/add")
    public String add(@ModelAttribute("manufacturer") @Valid Manufacturer manufacturer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "manufacturer/add";
        }
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturer";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/manufacturer/filter")
    public String manufacturerFilter(Model model)
    {
        return "manufacturer/filter";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/manufacturer/filter/result")
    public String manufacturerResult(@RequestParam String name, Model model)
    {
        List<Manufacturer> result = manufacturerRepository.findByNameContains(name);
        model.addAttribute("result", result);
        return "manufacturer/filter";
    }

    @GetMapping("/manufacturer/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        ArrayList<Manufacturer> res = new ArrayList<>();
        manufacturer.ifPresent(res::add);
        model.addAttribute("manufacturers", res);
        if (!manufacturerRepository.existsById(id)) {
            return "redirect:/manufacturer";
        }
        return "manufacturer/details";
    }

    @GetMapping("/manufacturer/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow();
        model.addAttribute("manufacturer",manufacturer);
        if (!manufacturerRepository.existsById(id)) {
            return "redirect:/manufacturer";
        }
        return "manufacturer/edit";
    }

    @PostMapping("/manufacturer/{id}/edit")
    public String updatePost(@PathVariable("id") long id, @ModelAttribute ("manufacturer")
    @Valid Manufacturer manufacturer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "manufacturer/edit";
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturer";
    }

    @PostMapping("/manufacturer/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow();
        manufacturerRepository.delete(manufacturer);
        return "redirect:/manufacturer";
    }
}
