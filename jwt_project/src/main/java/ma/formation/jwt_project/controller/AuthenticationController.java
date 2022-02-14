package ma.formation.jwt_project.controller;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.formation.jwt_project.domaine.RoleVo;
import ma.formation.jwt_project.domaine.TokenVo;
import ma.formation.jwt_project.domaine.UserVo;
import ma.formation.jwt_project.jwt.JwtUtils;
import ma.formation.jwt_project.service.IUserService;
import ma.formation.jwt_project.service.exception.BusinessException;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<TokenVo> authenticateUser(@Valid @RequestBody UserVo userVo) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            TokenVo tokenVo = new TokenVo();
            tokenVo.setJwttoken(jwt);
            tokenVo.setUsername(userVo.getUsername());
            Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
            list.forEach(authorite -> tokenVo.getRoles().add(authorite.getAuthority())); return ResponseEntity.ok(tokenVo);
        } catch (Exception e) {
            throw new BusinessException("Login ou mot de passe incorrect");
        }
    }
    @PostMapping("/signup") public ResponseEntity<?> registerUser(@Valid @RequestBody UserVo userVo) {
        if (userService.existsByUsername(userVo.getUsername())) { return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        // par d faut, le client a le r le CLIENT
        userVo.getRoles().add(new RoleVo("CLIENT"));
        userService.save(userVo); return ResponseEntity.ok("User registered successfully!");
    }
}