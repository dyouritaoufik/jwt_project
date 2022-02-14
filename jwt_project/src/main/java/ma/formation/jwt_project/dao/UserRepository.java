package ma.formation.jwt_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.formation.jwt_project.service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    boolean existsByUsername(String username);

}