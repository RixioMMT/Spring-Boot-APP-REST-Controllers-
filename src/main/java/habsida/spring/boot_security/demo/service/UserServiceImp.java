package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public List<User> printAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void addUser(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        user.getRoles().clear();
        userRepository.deleteById(id);
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }
    @Override
    public void editUser(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
