package ma.formation.jwt_project.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ma.formation.jwt_project.service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByRole(String role);
    List<Role> findAll();
}