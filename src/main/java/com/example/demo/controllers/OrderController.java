package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.OrderRepository;
import com.example.demo.repo.ProductRepository;
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
public class OrderController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order")
    public String main(Model model) {
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order/main";
    }

    @GetMapping("/order/add")
    public String add(Order order, Model model)
    {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "order/add";
    }

    @PostMapping("/order/add")
    public String postAdd(@ModelAttribute("order")  @Valid Order order, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {
            Iterable<Product> product = productRepository.findAll();
            model.addAttribute("product", product);
            return "order/add";
        }
        orderRepository.save(order);
        return "redirect:/order";
    }

    @GetMapping("/order/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<Order> order = orderRepository.findById(id);
        ArrayList<Order> res = new ArrayList<>();
        order.ifPresent(res::add);
        model.addAttribute("order", res);
        if (!orderRepository.existsById(id)) {
            return "redirect:/order";
        }
        return "order/details";
    }

    @GetMapping("/order/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {

        Order order = orderRepository.findById(id).orElseThrow();
        model.addAttribute("order",order);
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        if (!orderRepository.existsById(id)) {
            return "redirect:/order";
        }
        return "order/edit";
    }

    @PostMapping("/order/{id}/edit")
    public String postUpdate(@PathVariable("id") long id,@ModelAttribute ("order")
    @Valid Order order, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "order/edit";
        orderRepository.save(order);
        return "redirect:/order";
    }


    @PostMapping("/order/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
        return "redirect:/order";
    }
}
