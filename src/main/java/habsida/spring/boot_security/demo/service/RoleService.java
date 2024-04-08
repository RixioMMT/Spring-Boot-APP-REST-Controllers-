package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Role> getAllRoles();
    public Role getRoleById(Long id);
}
