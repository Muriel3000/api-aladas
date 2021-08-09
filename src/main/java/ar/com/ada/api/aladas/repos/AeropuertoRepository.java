package ar.com.ada.api.aladas.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.aladas.entities.Aeropuerto;

public interface AeropuertoRepository extends JpaRepository <Aeropuerto, Integer> {
    Aeropuerto findByCodigoIATA(String codigoIATA);
}
