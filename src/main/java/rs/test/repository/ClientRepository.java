package rs.test.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.test.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	Double save(double frequency);
	
	Boolean save(Boolean availability);

}
