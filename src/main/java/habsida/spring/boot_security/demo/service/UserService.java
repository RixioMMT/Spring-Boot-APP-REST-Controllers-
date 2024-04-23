package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> printAllUsers();
    public void addUser(User user);
    public void deleteUser(Long id);
    public User getUserById(Long id);
    public void editUser(User user);
}
