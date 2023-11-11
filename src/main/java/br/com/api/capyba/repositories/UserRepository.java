package br.com.api.capyba.repositories;

import br.com.api.capyba.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserDetails findByLogin(String login);

    @Query("SELECT u FROM UserModel u WHERE u.login = :login")
    UserModel findByLoginModel(String login);
}
