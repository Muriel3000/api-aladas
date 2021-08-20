package ar.com.ada.api.aladas.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasajero;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.ReservaRepository;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository repo;

    @Autowired
    private VueloService vueloService;

    @Autowired
    private PasajeroService pasajeroService;

    public Integer generarReserva(Integer vueloId, Integer pasajeroId){
        
        Reserva reserva = new Reserva();
        
        Pasajero pasajero = pasajeroService.buscarPorId(pasajeroId);      
        Vuelo vuelo = vueloService.buscarPorId(vueloId);

        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);
        reserva.setFechaEmision(new Date());
       
        Calendar c = Calendar.getInstance();
        c.setTime(reserva.getFechaEmision());
        c.add(Calendar.DATE, 1);
        reserva.setFechaVencimiento(c.getTime());
        
        vuelo.agregarReserva(reserva);
        pasajero.agregarReserva(reserva);

        repo.save(reserva);

        return reserva.getReservaId();
    }

    public List<Reserva> traerReservasDeVuelo(Integer vueloId){
        Vuelo vuelo = vueloService.buscarPorId(vueloId);
        return vuelo.getReservas();
    }

    public Reserva buscarPorId(Integer id){
        return repo.findByReservaId(id);
    }
}
