package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.RoleService;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Set<String> userRoles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRoles", userRoles);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "admin";
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
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String userIdString) {
        Long userId = Long.valueOf(userIdString);
        userService.deleteUser(userId);
        return "redirect:/admin";
    }
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        userService.editUser(user, roleIds);
        return "redirect:/admin";
    }
}
