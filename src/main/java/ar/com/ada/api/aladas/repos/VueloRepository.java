package ar.com.ada.api.aladas.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.aladas.entities.Vuelo;

public interface VueloRepository extends JpaRepository <Vuelo, Integer> {
    Vuelo findByVueloId(Integer id);
    List<Vuelo> findByEstadoVueloId(Integer estadoVueloId);
}
