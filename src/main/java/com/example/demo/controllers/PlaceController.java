package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Place;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.PlaceRepository;
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
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping("/place")
    public String main(Model model) {
        Iterable<Place> places = placeRepository.findAll();
        model.addAttribute("places", places);
        return "place/main";
    }

    @GetMapping("/place/add")
    public String add(Place place, Model model)
    {
        return "place/add";
    }

    @PostMapping("/place/add")
    public String add(@ModelAttribute("place") @Valid Place place, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "place/add";
        }
        placeRepository.save(place);
        return "redirect:/place";
    }

    @GetMapping("/place/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Place> place = placeRepository.findById(id);
        ArrayList<Place> res = new ArrayList<>();
        place.ifPresent(res::add);
        model.addAttribute("places", res);
        if (!placeRepository.existsById(id)) {
            return "redirect:/place";
        }
        return "place/details";
    }

    @GetMapping("/place/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Place place = placeRepository.findById(id).orElseThrow();
        model.addAttribute("place",place);
        if (!placeRepository.existsById(id)) {
            return "redirect:/place";
        }
        return "place/edit";
    }

    @PostMapping("/place/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("place")
    @Valid Place place, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "place/edit";
        placeRepository.save(place);
        return "redirect:/place";
    }

    @PostMapping("/place/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Place place = placeRepository.findById(id).orElseThrow();
        placeRepository.delete(place);
        return "redirect:/place";
    }
}
