package com.pns.repository;


import com.pns.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client , Long> {

    Client findByClientId(Long clientId);

    Client findByClientIdAndBrowser(Long clientId, String browser);


}
