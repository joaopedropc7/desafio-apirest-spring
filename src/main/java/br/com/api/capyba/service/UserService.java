package br.com.api.capyba.service;

import br.com.api.capyba.exceptions.ResourceNotFoundException;
import br.com.api.capyba.infra.security.TokenService;
import br.com.api.capyba.models.UserModel;
import br.com.api.capyba.models.records.UpdateAccountDTO;
import br.com.api.capyba.models.records.UpdatePasswordDTO;
import br.com.api.capyba.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

     public UserModel getIdUserByAuthentication(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Authentication authentication = tokenService.getAuthentication(token);
        UserModel userModel = (UserModel) authentication.getPrincipal();
        return userModel;
    }

    public UserModel updateAccount(UpdateAccountDTO updateAccountDTO, HttpServletRequest request){
         UserModel userModel = getIdUserByAuthentication(request);
         userModel.setLogin(updateAccountDTO.login());
         userModel.setName(updateAccountDTO.name());
         userModel.setEmail(updateAccountDTO.email());
         userModel.setNumber(updateAccountDTO.number());
         userRepository.save(userModel);
         return userModel;
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO, HttpServletRequest request){
        UserModel userModel = getIdUserByAuthentication(request);


        boolean passwordEquals = new BCryptPasswordEncoder().matches(updatePasswordDTO.password(), userModel.getPassword());

        if(!passwordEquals){
            throw new ResourceNotFoundException("This password is not the same as the current one");
        }
        String newPasswordDTO = new BCryptPasswordEncoder().encode(updatePasswordDTO.newPassword());
        userModel.setPassword(newPasswordDTO);
        userRepository.save(userModel);
    }
}
