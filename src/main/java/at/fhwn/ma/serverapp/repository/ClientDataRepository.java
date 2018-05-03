package at.fhwn.ma.serverapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.fhwn.ma.serverapp.model.ClientData;

@Repository
public interface ClientDataRepository extends JpaRepository<ClientData, Long>{

	Double save(double frequency);
	
	Boolean save(Boolean availability);

}
