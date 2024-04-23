package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.RoleService;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> printUsersInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = (User) userDetailsService.loadUserByUsername(userEmail);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("currentUser", currentUser);
        List<User> allUsers = userService.printAllUsers();
        responseData.put("allUsers", allUsers);
        List<Role> allRoles = roleService.getAllRoles();
        responseData.put("allRoles", allRoles);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        List<Role> roles = new ArrayList<>();
        for (Role roleDto : userDto.getRoles()) {
            Role role = roleService.getRoleById(roleDto.getId());
            roles.add(role);
        }
        user.setRoles(roles);
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<User> editUser(@PathVariable Long userId, @RequestBody User userDto) {
        User user = userService.getUserById(userId);
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        List<Role> roles = new ArrayList<>();
        for (Role roleDto : userDto.getRoles()) {
            Role role = roleService.getRoleById(roleDto.getId());
            roles.add(role);
        }
        user.setRoles(roles);
        userService.editUser(user);
        return ResponseEntity.ok(user);
    }

}