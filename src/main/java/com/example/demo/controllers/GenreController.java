package com.example.demo.controllers;

import com.example.demo.models.Genre;
import com.example.demo.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre")
    public String main(Model model) {
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "genre/genre-main";
    }

    @GetMapping("/genre/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String add(Genre genre, Model model)
    {
        return "genre/genre-add";
    }

    @PostMapping("/genre/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String add(@ModelAttribute("genre") @Valid Genre genre, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "genre/genre-add";
        }
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @GetMapping("/genre/filter")
    public String filter(Model model)
    {
        return "genre/genre-filter";
    }

    @PostMapping("/genre/filter/result")
    public String result(@RequestParam String title, Model model)
    {
        Iterable<Genre> result = genreRepository.getAllByTitle(title);
        model.addAttribute("result", result);
        return "genre/genre-filter";
    }

    @GetMapping("/genre/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Genre> genre = genreRepository.findById(id);
        ArrayList<Genre> res = new ArrayList<>();
        genre.ifPresent(res::add);
        model.addAttribute("genres", res);
        if (!genreRepository.existsById(id)) {
            return "redirect:/genre";
        }
        return "genre/genre-details";
    }

    @GetMapping("/genre/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        model.addAttribute("genre",genre);
        if (!genreRepository.existsById(id)) {
            return "redirect:/genre";
        }
        return "genre/genre-edit";
    }

    @PostMapping("/genre/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("genre")
    @Valid Genre genre, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "genre/genre-edit";
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @PostMapping("/genre/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genreRepository.delete(genre);
        return "redirect:/genre";
    }
}
