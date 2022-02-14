package ma.formation.jwt_project;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ma.formation.jwt_project.domaine.EmpVo;
import ma.formation.jwt_project.domaine.RoleVo;
import ma.formation.jwt_project.domaine.UserVo;
import ma.formation.jwt_project.service.IEmpService;
import ma.formation.jwt_project.service.IUserService;
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmpService empService;
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        userService.cleanDataBase();
        userService.save(new RoleVo("ADMIN"));
        userService.save(new RoleVo("CLIENT"));
        RoleVo roleAdmin = userService.getRoleByName("ADMIN");
        RoleVo roleClient = userService.getRoleByName("CLIENT");
        UserVo admin1 = new UserVo("admin1", "admin1", Arrays.asList(roleAdmin));
        UserVo admin2 = new UserVo("admin2", "admin2", Arrays.asList(roleAdmin));
        UserVo client1 = new UserVo("client1", "client1", Arrays.asList(roleClient));
        UserVo client2 = new UserVo("client2", "client2", Arrays.asList(roleClient));
        userService.save(admin1);
        userService.save(client1);
        userService.save(client2);
        userService.save(admin2);
// // *************
        empService.save(new EmpVo("emp1", 10000d, "Fonction1"));
        empService.save(new EmpVo("emp2", 20000d, "Fonction3"));
        empService.save(new EmpVo("emp3", 30000d, "Fonction4"));
        empService.save(new EmpVo("emp4", 40000d, "Fonction5"));
        empService.save(new EmpVo("emp5", 50000d, "Fonction6"));
    }
}