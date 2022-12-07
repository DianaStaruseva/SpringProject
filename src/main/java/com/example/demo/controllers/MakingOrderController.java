package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
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
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class MakingOrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MakingOrderRepository makingOrderRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/making_order")
    public String main(Model model) {
        Iterable<MakingOrder> making_orders = makingOrderRepository.findAll();
        model.addAttribute("making_orders", making_orders);
        return "making_order/main";
    }

    @GetMapping("/making_order/add")
    public String add(MakingOrder making_order, Order order, Model model)
    {
        Iterable<Buyer> buyers = buyerRepository.findAll();
        model.addAttribute("buyers", buyers);
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        Iterable<Place> places = placeRepository.findAll();
        model.addAttribute("places", places);
        return "making_order/add";
    }

    @PostMapping("/making_order/add")
    public String postAdd(@ModelAttribute("making_order") @Valid MakingOrder making_order, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {
            Iterable<Buyer> buyers = buyerRepository.findAll();
            model.addAttribute("buyers", buyers);
            Iterable<Order> orders = orderRepository.findAll();
            model.addAttribute("orders", orders);
            Iterable<Place> places = placeRepository.findAll();
            model.addAttribute("places", places);
            return "making_order/add";
        }
        makingOrderRepository.save(making_order);
        return "redirect:/making_order";
    }

    @GetMapping("/making_order/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        Optional<MakingOrder> making_order = makingOrderRepository.findById(id);
        ArrayList<MakingOrder> res = new ArrayList<>();
        making_order.ifPresent(res::add);
        model.addAttribute("making_order", res);
        if (!makingOrderRepository.existsById(id)) {
            return "redirect:/making_order";
        }
        return "making_order/details";
    }


    @GetMapping("/making_order/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {

        MakingOrder making_order = makingOrderRepository.findById(id).orElseThrow();
        model.addAttribute("making_order",making_order);
        Iterable<Buyer> buyers = buyerRepository.findAll();
        model.addAttribute("buyers", buyers);
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        Iterable<Place> places = placeRepository.findAll();
        model.addAttribute("places", places);
        if (!makingOrderRepository.existsById(id)) {
            return "redirect:/making_order";
        }
        return "making_order/edit";
    }

    @PostMapping("/making_order/{id}/edit")
    public String postUpdate(@PathVariable("id") long id,@ModelAttribute ("making_order")
    @Valid MakingOrder making_order, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "making_order/edit";
        makingOrderRepository.save(making_order);
        return "redirect:/making_order";
    }

    @PostMapping("/making_order/{id}/remove")
    public String delete(@PathVariable("id") long id, Model model) {
        MakingOrder making_order = makingOrderRepository.findById(id).orElseThrow();
        makingOrderRepository.delete(making_order);
        return "redirect:/making_order";
    }
}
