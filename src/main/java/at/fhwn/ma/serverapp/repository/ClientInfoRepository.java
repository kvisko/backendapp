package at.fhwn.ma.serverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.fhwn.ma.serverapp.model.Client;

@Repository
public interface ClientInfoRepository extends JpaRepository<Client, Long>{

}
