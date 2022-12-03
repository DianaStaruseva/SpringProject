package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
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
@PreAuthorize("hasAnyAuthority('USER')")
public class BlogController  {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog/blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Post post, Model model) {
        return "blog/blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAdd(@ModelAttribute("post") @Valid Post post,
                          BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "blog/blog-add";
        }
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog/blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        // List<Post> result = postRepository.findLikeTitle(title);
        model.addAttribute("result", result);
        return "blog/blog-filter";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        return "blog/blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEditGet(@PathVariable("id") long id, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        model.addAttribute("post",post);
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        return "blog/blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id,@ModelAttribute ("post")
    @Valid Post post, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "blog/blog-edit";
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogBlogDelete(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
