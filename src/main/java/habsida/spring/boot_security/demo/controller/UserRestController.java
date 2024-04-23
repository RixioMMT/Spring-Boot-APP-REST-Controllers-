package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private UserDetailsService userDetailsService;
    @GetMapping
    public ResponseEntity<User> printUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = (User) userDetailsService.loadUserByUsername(userEmail);
        return ResponseEntity.ok(currentUser);
    }
}