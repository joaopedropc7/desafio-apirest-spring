package br.com.api.capyba.service;

import br.com.api.capyba.exceptions.ResourceNotFoundException;
import br.com.api.capyba.infra.security.SecurityFilter;
import br.com.api.capyba.infra.security.TokenService;
import br.com.api.capyba.models.UserModel;
import br.com.api.capyba.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }


    public void verifiedAccount(Integer id){
        UserModel user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setVerifiedEmail(true);
        repository.save(user);
    }

}
