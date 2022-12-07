package com.example.demo.controllers;

import com.example.demo.models.Buyer;
import com.example.demo.models.Form;
import com.example.demo.repo.BuyerRepository;
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
@PreAuthorize("hasAnyAuthority('USER')")
public class BuyerController {
    @Autowired
    private BuyerRepository buyerRepository;

    @GetMapping("/buyer")
    public String main(Model model) {
        Iterable<Buyer> buyers = buyerRepository.findAll();
        model.addAttribute("buyers", buyers);
        return "buyer/main";
    }

    @GetMapping("/buyer/add")
    public String add(Buyer buyer, Model model)
    {
        return "buyer/add";
    }

    @PostMapping("/buyer/add")
    public String add(@ModelAttribute("buyer") @Valid Buyer buyer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "buyer/add";
        }
        buyerRepository.save(buyer);
        return "redirect:/buyer";
    }
    @GetMapping("/buyer/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Buyer> buyer = buyerRepository.findById(id);
        ArrayList<Buyer> res = new ArrayList<>();
        buyer.ifPresent(res::add);
        model.addAttribute("buyers", res);
        if (!buyerRepository.existsById(id)) {
            return "redirect:/buyer";
        }
        return "buyer/details";
    }

    @GetMapping("/buyer/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Buyer buyer = buyerRepository.findById(id).orElseThrow();
        model.addAttribute("buyer",buyer);
        if (!buyerRepository.existsById(id)) {
            return "redirect:/buyer";
        }
        return "buyer/edit";
    }

    @PostMapping("/buyer/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("buyer")
    @Valid Buyer buyer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "buyer/edit";
        buyerRepository.save(buyer);
        return "redirect:/buyer";
    }

    @PostMapping("/buyer/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Buyer buyer = buyerRepository.findById(id).orElseThrow();
        buyerRepository.delete(buyer);
        return "redirect:/buyer";
    }
}
