package br.com.api.capyba.controller;

import br.com.api.capyba.infra.security.SecurityFilter;
import br.com.api.capyba.infra.security.TokenService;
import br.com.api.capyba.models.EmailModel;
import br.com.api.capyba.models.UserModel;
import br.com.api.capyba.models.enums.UserRole;
import br.com.api.capyba.models.records.*;
import br.com.api.capyba.repositories.UserRepository;
import br.com.api.capyba.service.AuthorizationService;
import br.com.api.capyba.service.EmailService;
import br.com.api.capyba.service.TokenVerifyService;
import br.com.api.capyba.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenVerifyService tokenVerifyService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO data, HttpServletRequest request){
        UserModel user = repository.findByLoginModel(data.login());
        if(!user.getVerifiedEmail()){
            return ResponseEntity.badRequest().body("Email não verificado");
        }
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
        Integer token =  tokenVerifyService.generateTokenVerified(newUser.getId());
        var recipient = data.email();
        var messageBody = "Olá, " + newUser.getLogin() + "! \n\n" +
                "Seja bem-vindo(a) a Esphera! \n\n" +
                "Para confirmar seu cadastro, insira este token: \n\n" +
                token + "\n\n" +
                "Atenciosamente, \n" +
                "Equipe Esphera";
        var subject = "Confirmação de cadastro";
        EmailModel emailModel = new EmailModel(recipient, messageBody, subject, null);
        emailService.sendEmail(emailModel);

        return ResponseEntity.ok().build().ok("Usuário cadastrado com sucesso, Verifique seu email para confirmar o cadastro!");
    }

    @PutMapping("/update")
    public ResponseEntity<UserModel> updateAccount(HttpServletRequest request,@RequestBody UpdateAccountDTO updateAccountDTO){
        UserModel user = userService.updateAccount(updateAccountDTO, request);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/password")
    public ResponseEntity updatePassword(HttpServletRequest request,@RequestBody UpdatePasswordDTO updatePasswordDTO){
        userService.updatePassword(updatePasswordDTO, request);
        return ResponseEntity.ok().body("Senha alterada com sucesso!");
    }

    @PostMapping("/logout")
    public ResponseEntity logoutAccount(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            var credentials = auth.getCredentials();

            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok().build();

    }

   @PostMapping("/verified/{id}")
    public ResponseEntity verifiedEmail(@PathVariable(value = "id") Integer idUser, @RequestBody TokenRecordDTO token){
        Boolean verify = tokenVerifyService.verifyTokenEqualsDB(idUser, token.token());
        if (verify){
            return ResponseEntity.ok("Email verificado com sucesso!");
        }
        return ResponseEntity.badRequest().body("Token incorreto ou expirado");
    }



}
