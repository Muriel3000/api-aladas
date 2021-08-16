package ar.com.ada.api.aladas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table (name = "pasajero")
public class Pasajero extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pasajero_id")
    private Integer pasajeroId;

    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToOne(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // CascadeType.ALL -> fk, cuando se cree un staff/pasajero se va a crear 
    //tambien un usuario(relacion bidireccional)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setPasajero(this);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getPasajeroId(){
        return pasajeroId;
    }

    public void setPasajeroId(Integer pasajeroId){
        this.pasajeroId = pasajeroId;
    }

    public void agregarReserva(Reserva reserva){
        this.reservas.add(reserva);
        reserva.setPasajero(this);
    }
    
}
