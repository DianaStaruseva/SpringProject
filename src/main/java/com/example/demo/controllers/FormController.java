package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Form;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class FormController {
    @Autowired
    private FormRepository formRepository;

    @GetMapping("/form")
    public String main(Model model) {
        Iterable<Form> forms = formRepository.findAll();
        model.addAttribute("forms", forms);
        return "form/main";
    }


    @GetMapping("/form/add")
    public String add(Form form, Model model)
    {
        return "form/add";
    }

    @PostMapping("/form/add")
    public String add(@ModelAttribute("form") @Valid Form form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form/add";
        }
        formRepository.save(form);
        return "redirect:/form";
    }

    @GetMapping("/form/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Form> form = formRepository.findById(id);
        ArrayList<Form> res = new ArrayList<>();
        form.ifPresent(res::add);
        model.addAttribute("forms", res);
        if (!formRepository.existsById(id)) {
            return "redirect:/form";
        }
        return "form/details";
    }

    @GetMapping("/form/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Form form = formRepository.findById(id).orElseThrow();
        model.addAttribute("form",form);
        if (!formRepository.existsById(id)) {
            return "redirect:/form";
        }
        return "form/edit";
    }

    @PostMapping("/form/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("form")
    @Valid Form form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "form/edit";
        formRepository.save(form);
        return "redirect:/form";
    }

    @PostMapping("/form/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Form form = formRepository.findById(id).orElseThrow();
        formRepository.delete(form);
        return "redirect:/form";
    }
}
