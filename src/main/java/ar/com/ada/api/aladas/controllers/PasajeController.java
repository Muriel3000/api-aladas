package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.models.request.InfoPasajeRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.PasajeService;
import ar.com.ada.api.aladas.services.ReservaService;

@RestController
public class PasajeController {

    @Autowired 
    private PasajeService service;
    @Autowired
    private ReservaService reservaService;

    @PostMapping("/api/pasajes")
    public ResponseEntity<GenericResponse> emitir(@RequestBody InfoPasajeRequest infoPasaje){
       
        GenericResponse response = new GenericResponse();

        Reserva reserva = reservaService.buscarPorId(infoPasaje.reservaId);
        Integer pasajeId = service.emitir(reserva);

        response.isOk = true;
        response.message = "El pasaje ha sido generado";
        response.id = pasajeId;

        return ResponseEntity.ok(response);
    }
    
}
