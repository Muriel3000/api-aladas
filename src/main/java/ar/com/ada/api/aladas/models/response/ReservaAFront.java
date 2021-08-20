package ar.com.ada.api.aladas.models.response;

import java.util.Date;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;

public class ReservaAFront {
    public Integer reservaId;
    public EstadoReservaEnum estadoReservaId;
    public Date fechaEmision;
    public Date fechaVencimiento;
    public Integer pasajeroId;
    public String username;

    public static ReservaAFront convertirDesde(Reserva r){
        ReservaAFront reservaMostrar = new ReservaAFront();
        reservaMostrar.reservaId = r.getReservaId();
        reservaMostrar.estadoReservaId = r.getEstadoReservaId();
        reservaMostrar.fechaEmision = r.getFechaEmision();
        reservaMostrar.fechaVencimiento = r.getFechaVencimiento();
        reservaMostrar.pasajeroId = r.getPasajero().getPasajeroId();
        reservaMostrar.username = r.getPasajero().getUsuario().getUsername();
        return reservaMostrar;
    }
}
