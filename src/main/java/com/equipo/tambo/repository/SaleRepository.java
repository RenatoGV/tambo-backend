package com.equipo.tambo.repository;

import com.equipo.tambo.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    boolean existsByUserId(Long userId);
    boolean existsByClientId(Long clientId);
}