package br.com.api.capyba.controller;

import br.com.api.capyba.infra.security.TokenService;
import br.com.api.capyba.models.UserModel;
import br.com.api.capyba.models.enums.UserRole;
import br.com.api.capyba.models.records.LoginDTO;
import br.com.api.capyba.models.records.LoginResponseDTO;
import br.com.api.capyba.models.records.RegisterDTO;
import br.com.api.capyba.repositories.UserRepository;
import br.com.api.capyba.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data, encryptedPassword);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity logoutAccount(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verified/{id}")
    public ResponseEntity verifiedEmail(@PathVariable(value = "id") Integer id){
        authorizationService.verifiedAccount(id);
        return ResponseEntity.ok().build();
    }
}
