package ar.com.ada.api.aladas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.PasajeRepository;

@Service
public class PasajeService {
    
    @Autowired
    private PasajeRepository repo;
    @Autowired
    private VueloService vueloService;

    public Integer emitir(Reserva reserva){
        
        Pasaje pasaje = new Pasaje();
        pasaje.setFechaEmision(new Date());
        
        reserva.asociarPasaje(pasaje);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);

        Vuelo vuelo = vueloService.buscarPorId(reserva.getVuelo().getVueloId());
        vuelo.setCapacidad(vuelo.getCapacidad() - 1);

        /* problema concurrencia:
        * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
        * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
        * puede suceder al mismo tiempo
        */

        vueloService.actualizar(vuelo);

        return pasaje.getPasajeId();
    }
}
