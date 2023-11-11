package br.com.api.capyba.repositories;

import br.com.api.capyba.models.TokenModel;
import br.com.api.capyba.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenModel, Integer> {

    Optional<TokenModel> findByUserAndToken(UserModel user, Integer token);


}
