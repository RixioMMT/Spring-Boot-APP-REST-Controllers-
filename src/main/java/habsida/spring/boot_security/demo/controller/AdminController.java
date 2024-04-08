package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.RoleService;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;
    @GetMapping
    public String printUsers(Model model) {
        model.addAttribute("users", userService.printAllUsers());
        return "admin";
    }
    @GetMapping("/add")
    public String addUserWeb(Model model) {
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("user", new User());
        return "add";
    }
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        List<Role> roles = roleIds.stream()
                .map(roleService::getRoleById)
                .collect(Collectors.toList());
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String editUserWeb(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("user", user);
        return "edit";
    }
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        userService.editUser(user, roleIds);
        return "redirect:/admin";
    }

}
