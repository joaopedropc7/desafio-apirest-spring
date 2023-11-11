package br.com.api.capyba.service;

import br.com.api.capyba.exceptions.ResourceNotFoundException;
import br.com.api.capyba.models.TokenModel;
import br.com.api.capyba.models.UserModel;
import br.com.api.capyba.repositories.TokenRepository;
import br.com.api.capyba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TokenVerifyService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Integer generateTokenVerified(Integer idUser){
        UserModel userModel = userRepository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("No users found for this id!"));
        Random generator = new Random();
        Integer token = generator.nextInt(90000) + 10000;
        TokenModel tokenModel = new TokenModel(token, userModel);
        tokenRepository.save(tokenModel);
        return tokenModel.getToken();
    }

    public Boolean verifyTokenEqualsDB(Integer idUser, Integer token){
        UserModel userModel = userRepository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("No users found for this id!"));
        Optional<TokenModel> tokenOptional = tokenRepository.findByUserAndToken(userModel, token);
        if (tokenOptional.isPresent()){
            TokenModel tokenModelDB = tokenOptional.get();
            if (tokenModelDB.getDateExpireToken().isBefore(LocalDateTime.now().plusMinutes(1))){
                return Boolean.FALSE;
            }else{
                userModel.setVerifiedEmail(true);
                userRepository.save(userModel);
                return Boolean.TRUE;
            }
        }
        return false;
    }
}
