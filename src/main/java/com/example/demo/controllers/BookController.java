package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.models.Post;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.GenreRepository;
import com.example.demo.viewModels.BookGenreViewModel;
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
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;


    @GetMapping("/book")
    public String booksMain(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/book-main";
    }


    @GetMapping("/book/add")
    public String bookAdd(Book book, Model model)
    {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "book/book-add";
    }

    @PostMapping("/book/add")
    public String bookPostAdd(@ModelAttribute ("book")  @Valid Book book, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {
            Iterable<Author> author = authorRepository.findAll();
            model.addAttribute("author",author);
            return "book/book-add";
        }
        bookRepository.save(book);
        return "redirect:/book";
    }

    @GetMapping("/book/{id}/genres")
    public String addGenres(@PathVariable("id") long id, Model model)
    {
        Iterable<Genre> genres = genreRepository.findAll();
        Book book = bookRepository.findById(id).get();
        model.addAttribute("genres", book.getGenres());
        model.addAttribute("viewModel", new BookGenreViewModel(genres));
        model.addAttribute("id", id);
        return "book/book-add-genres";
    }

    @PostMapping("/book/{id}/genre/add")
    public String addGenres(@PathVariable("id") long id, @ModelAttribute BookGenreViewModel viewModel, Model model)
    {
        Book book = bookRepository.findById(id).get();
        book.addGenre(viewModel.getSelectedGenre());
        bookRepository.save(book);

        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/book-main";
    }

    @GetMapping("/book/{idBook}/genre/{idGenre}/remove")
    public String removeGenres(@PathVariable("idBook") long idBook, @PathVariable("idGenre") long idGenre, Model model)
    {
        Book book = bookRepository.findById(idBook).get();
        Genre genre = genreRepository.findById(idGenre).get();
        book.removeGenre(genre);
        bookRepository.save(book);

        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/book-main";
    }

    @GetMapping("/book/filter")
    public String bookFilter(Model model)
    {
        return "book/book-filter";
    }

    @PostMapping("/book/filter/result")
    public String bookResult(@RequestParam String name, Model model)
    {
        List<Book> result = bookRepository.findByNameContains(name);
        model.addAttribute("result", result);
        return "book/book-filter";
    }


    @PostMapping("/book/filter/allResult")
    public String bookAllFilterResult(
            @RequestParam String name,
            Model model
    ) {
        List<Book> result = bookRepository.findByName(name);
        model.addAttribute("result", result);
        return "book/book-filter";
    }

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        if (!bookRepository.existsById(id)) {
            return "redirect:/book";
        }
        return "book/book-details";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable("id") long id, Model model) {

        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book",book);
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        if (!bookRepository.existsById(id)) {
            return "redirect:/book";
        }
        return "book/book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookPostUpdate(@PathVariable("id") long id,@ModelAttribute ("book")
    @Valid Book book, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "book/book-edit";
        bookRepository.save(book);
        return "redirect:/book";
    }

    @PostMapping("/book/{id}/remove")
    public String bookBookDelete(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/book";
    }
}
