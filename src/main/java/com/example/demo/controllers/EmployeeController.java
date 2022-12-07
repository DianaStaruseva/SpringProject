package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.Post;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.PostRepository;
import com.example.demo.viewModels.EmployeePostViewModel;
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
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository ;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/employee")
    public String main(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee/main";
    }

    @GetMapping("/employee/add")
    public String add(Employee employee, Model model)
    {
        return "employee/add";
    }

    @PostMapping("/employee/add")
    public String postAdd(@ModelAttribute("employee")  @Valid Employee employee,
                          BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()) {
            return "employee/add";
        }
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}/posts")
    public String addPosts(@PathVariable("id") long id, Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("posts", employee.getPosts());
        model.addAttribute("viewModel", new EmployeePostViewModel(posts));
        model.addAttribute("id", id);
        return "employee/employee-posts";
    }

    @PostMapping("/employee/{id}/post/add")
    public String addPosts(@PathVariable("id") long id, @ModelAttribute EmployeePostViewModel viewModel, Model model)
    {
        Employee employee = employeeRepository.findById(id).get();
        employee.addPost(viewModel.getSelectedPost());
        employeeRepository.save(employee);

        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee/main";
    }

    @GetMapping("/employee/{idEmployee}/post/{idPost}/remove")
    public String removePosts(@PathVariable("idEmployee") long idEmployee, @PathVariable("idPost") long idPost, Model model)
    {
        Employee employee = employeeRepository.findById(idEmployee).get();
        Post post = postRepository.findById(idPost).get();
        employee.removePost(post);
        employeeRepository.save(employee);

        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee/main";
    }

    @GetMapping("/employee/filter")
    public String employeeFilter(Model model)
    {
        return "employee/filter";
    }

    @PostMapping("/employee/filter/result")
    public String employeeResult(@RequestParam String surname, Model model)
    {
        List<Employee> result = employeeRepository.findBySurnameContains(surname);
        model.addAttribute("result", result);
        return "employee/filter";
    }

    @GetMapping("/employee/{id}")
    public String employeeDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        employee.ifPresent(res::add);
        model.addAttribute("employee", res);
        if (!employeeRepository.existsById(id)) {
            return "redirect:/employee";
        }
        return "employee/details";
    }

    @GetMapping("/employee/{id}/edit")
    public String employeeEdit(@PathVariable("id") long id, Model model) {

        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee",employee);
        if (!employeeRepository.existsById(id)) {
            return "redirect:/employee";
        }
        return "employee/edit";
    }

    @PostMapping("/employee/{id}/edit")
    public String employeeUpdate(@PathVariable("id") long id,@ModelAttribute ("employee")
    @Valid Employee employee, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "employee/edit";
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @PostMapping("/employee/{id}/remove")
    public String employeeDelete(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/employee";
    }
}
