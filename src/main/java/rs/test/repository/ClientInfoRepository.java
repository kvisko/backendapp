package rs.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.test.model.ClientInfo;

@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long>{

}
