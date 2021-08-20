package ar.com.ada.api.aladas.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.models.request.InfoReservaRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.models.response.ReservaAFront;
import ar.com.ada.api.aladas.services.ReservaService;
import ar.com.ada.api.aladas.services.UsuarioService;

@RestController
public class ReservaController {
    
    @Autowired 
    private ReservaService service;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> generarReserva(@RequestBody InfoReservaRequest infoReserva){
       
        // Obtengo a quien esta autenticado del otro lado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // De lo que esta autenticado, obtengo su USERNAME
        String username = authentication.getName();

        // Buscar el usuario por username
        Usuario usuario = usuarioService.buscarPorUsername(username);

        // con el usuario, obtengo el pasajero, y con ese, obtengo el Id
        Integer numeroReserva = service.generarReserva(infoReserva.vueloId, usuario.getPasajero().getPasajeroId());
        
        GenericResponse response = new GenericResponse();
        response.isOk = true;
        response.message = "La reserva fue realizada con exito";
        response.id = numeroReserva;
       
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/vuelos/{vueloId}/reservas")
    public ResponseEntity<List<ReservaAFront>> getReservasDeVuelo(@PathVariable Integer vueloId){
        List<Reserva> reservas = service.traerReservasDeVuelo(vueloId);
        List<ReservaAFront> reservasMostrar = new ArrayList();
        for(Reserva r : reservas){
            ReservaAFront reservaModel = ReservaAFront.convertirDesde(r);
            reservasMostrar.add(reservaModel);
        }
        return ResponseEntity.ok(reservasMostrar);
    }
}
