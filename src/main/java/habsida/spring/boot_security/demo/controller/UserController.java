package habsida.spring.boot_security.demo.controller;


import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String printUser(Model model) {
        model.addAttribute("users", userService.printAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Set<String> userRoles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRoles", userRoles);
        return "user";
    }
}
