package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
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
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post")
    public String main(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "post/main";
    }

    @GetMapping("/post/add")
    public String add(Post post, Model model)
    {
        return "post/add";
    }

    @PostMapping("/post/add")
    public String add(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/add";
        }
        postRepository.save(post);
        return "redirect:/post";
    }

    @GetMapping("/post/filter")
    public String filter(Model model)
    {
        return "post/filter";
    }

    @PostMapping("/post/filter/result")
    public String result(@RequestParam String name, Model model)
    {
        Iterable<Post> result = postRepository.getAllByName(name);
        model.addAttribute("result", result);
        return "post/filter";
    }

    @GetMapping("/post/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("posts", res);
        if (!postRepository.existsById(id)) {
            return "redirect:/post";
        }
        return "post/details";
    }

    @GetMapping("/post/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        model.addAttribute("post",post);
        if (!postRepository.existsById(id)) {
            return "redirect:/post";
        }
        return "post/edit";
    }

    @PostMapping("/post/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute ("post")
    @Valid Post post, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "post/edit";
        postRepository.save(post);
        return "redirect:/post";
    }

    @PostMapping("/post/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/post";
    }
}
