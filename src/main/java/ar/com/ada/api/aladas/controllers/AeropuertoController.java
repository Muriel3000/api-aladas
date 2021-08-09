package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;

@RestController
public class AeropuertoController {
    
    @Autowired
    private AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<?> crear(@RequestBody Aeropuerto aeropuerto){ 
        service.crear(aeropuerto.getAeropuertoId(), aeropuerto.getNombre(), aeropuerto.getCodigoIATA());
        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.message = "Se creo correctamente";
        respuesta.id = aeropuerto.getAeropuertoId();

        return ResponseEntity.ok(respuesta);
    }
}
