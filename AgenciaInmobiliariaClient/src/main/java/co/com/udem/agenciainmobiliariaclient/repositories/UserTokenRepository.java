package co.com.udem.agenciainmobiliariaclient.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliariaclient.entities.UserToken;

public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

	
	@Query("SELECT u FROM UserToken u WHERE u.username = ?1")
    public String obtenerToken(String username);
	
}
