package ar.com.ada.api.aladas.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasajero;
import ar.com.ada.api.aladas.repos.PasajeroRepository;

@Service
public class PasajeroService {
    
    @Autowired 
    private PasajeroRepository repo;

    public void crearPasajero(Pasajero pasajero){
        repo.save(pasajero);
    }
}
