package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
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
@PreAuthorize("hasAnyAuthority('AUTHOR', 'ADMIN')")
public class AuthorController  {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/author")
    public String authorsMain(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author/author-main";
    }

    @GetMapping("/author/add")
    public String authorAdd(Author author, Model model)
    {
        return "author/author-add";
    }

    @PostMapping("/author/add")
    public String authorPostAdd(@ModelAttribute ("author")  @Valid Author author, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return "author/author-add";
        authorRepository.save(author);
        return "redirect:/author";

    }

    @GetMapping("/author/filter")
    public String authorFilter(Model model)
    {
        return "author/author-filter";
    }

    @PostMapping("/author/filter/allResult")
    public String authorAllFilterResult(
            @RequestParam String surname,
            Model model)
    {
        List<Author> result = authorRepository.findBySurname(surname);
        model.addAttribute("result", result);
        return "author/author-filter";
    }

    @GetMapping("/author/{id}")
    public String authorDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        if (!authorRepository.existsById(id)) {
            return "redirect:/author";
        }
        return "author/author-details";
    }

    @GetMapping("/author/{id}/edit")
    public String authorEditGet(@PathVariable("id") long id, Model model) {

        Author author = authorRepository.findById(id).orElseThrow();
        model.addAttribute("author",author);
        if (!authorRepository.existsById(id)) {
            return "redirect:/author";
        }
        return "author/author-edit";
    }

    @PostMapping("/author/{id}/edit")
    public String authorPostUpdate(@PathVariable("id") long id,@ModelAttribute ("author")
                                    @Valid Author author, BindingResult bindingResult) {

       if(bindingResult.hasErrors()) return "author/author-edit";
        authorRepository.save(author);
        return "redirect:/author";
    }

    @PostMapping("/author/{id}/remove")
    public String authorAuthorDelete(@PathVariable("id") long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        authorRepository.delete(author);
        return "redirect:/author";
    }
}
