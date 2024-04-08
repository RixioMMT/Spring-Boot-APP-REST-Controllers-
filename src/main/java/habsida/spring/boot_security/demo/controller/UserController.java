package habsida.spring.boot_security.demo.controller;


import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String printUsers(Model model) {
        model.addAttribute("users", userService.printAllUsers());
        return "user";
    }
}
