package com.example.demo.controllers;

import com.example.demo.models.LibraryCard;
import com.example.demo.models.Reader;
import com.example.demo.models.Role;
import com.example.demo.repo.LibraryCardRepository;
import com.example.demo.repo.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@PreAuthorize("hasAnyAuthority('USER')")
public class ReaderController {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @GetMapping("/reader")
    public String Main(Model model){
        Iterable<LibraryCard> library_card = libraryCardRepository.findAll();
        model.addAttribute("library_card", library_card);
        return "reader";
    }

    @PostMapping("/reader/add")
    public String readerPostAdd(@RequestParam String name, @RequestParam String number, Model model)
    {
        System.out.println(name);
        LibraryCard library_card = libraryCardRepository.findByNumber(number);
        System.out.println(library_card.getId());
        Reader reader = new Reader(name, library_card);
        readerRepository.save(reader);
        return "reader";
    }

}
