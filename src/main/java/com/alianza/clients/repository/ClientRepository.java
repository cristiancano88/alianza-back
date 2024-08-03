package com.alianza.clients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alianza.clients.model.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE LOWER(c.sharedKey) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Client> searchByTerm(@Param("searchTerm") String searchTerm);

    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:phone IS NULL OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :phone, '%'))) AND " +
            "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<Client> findByFilters(@Param("name") String name, @Param("phone") String phone, @Param("email") String email);
}
