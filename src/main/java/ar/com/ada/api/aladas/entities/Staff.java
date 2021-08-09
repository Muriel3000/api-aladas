package ar.com.ada.api.aladas.entities;

import javax.persistence.*;;

@Entity
@Table(name = "staff")
public class Staff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // CascadeType.ALL -> fk, cuando se cree un staff/pasajero se va a crear 
    //tambien un usuario(relacion bidireccional)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setStaff(this);
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}
