package ar.com.ada.api.aladas.services;

import java.util.Collection;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasajero;
import ar.com.ada.api.aladas.entities.Staff;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.entities.Pais.PaisEnum;
import ar.com.ada.api.aladas.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.aladas.entities.Usuario.TipoUsuarioEnum;
import ar.com.ada.api.aladas.repos.UsuarioRepository;
import ar.com.ada.api.aladas.security.Crypto;
import ar.com.ada.api.aladas.sistema.comm.EmailService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasajeroService pasajeroService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private EmailService emailService;
    
    public Usuario crearUsuario(TipoUsuarioEnum tipoUsuario, String nombre, int pais, Date fechaNacimiento,
    TipoDocuEnum tipoDoc, String doc, String email, String password){
       
        Usuario u = new Usuario();
        u.setUsername(email);
        u.setEmail(email);
        u.setPassword(Crypto.encrypt(password, email.toLowerCase()));
        u.setTipoUsuario(tipoUsuario);

        if (tipoUsuario ==  TipoUsuarioEnum.PASAJERO){
            Pasajero p = new Pasajero();
            p.setNombre(nombre);
            p.setDocumento(doc);
            p.setTipoDocumentoId(tipoDoc);
            p.setFechaNacimiento(fechaNacimiento);
            p.setPaisId(PaisEnum.parse(pais));
            p.setUsuario(u);
            //repo.save(u); -> no funcionaria, porque la relacion bidireccional esta en staff 
            pasajeroService.crearPasajero(p);
        }
        else if (tipoUsuario == TipoUsuarioEnum.STAFF){
            Staff s = new Staff();
            s.setNombre(nombre);
            s.setDocumento(doc);
            s.setTipoDocumentoId(tipoDoc);
            s.setFechaNacimiento(fechaNacimiento);
            s.setPaisId(PaisEnum.parse(pais));
            s.setUsuario(u);
            //repo.save(u); -> no lo guarda en la db
            staffService.crearStaff(s);
        }

        emailService.SendEmail(u.getEmail(), "Registracion exitosa", "Bienvenido, ud ha sido registrado");

        return u;
    }
        
    public Usuario login(String username, String password) {

        // Metodo IniciarSesion recibe usuario y contraseña: validar usuario y contraseña
             
        Usuario u = buscarPorUsername(username);
        
        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {
            throw new BadCredentialsException("Usuario o contraseña invalida");
        }
        
        return u;
    }

    public Usuario buscarPorUsername(String username){
        return repo.findByUsername(username);
    }

    public UserDetails getUserAsUserDetail(Usuario usuario) {
        
        UserDetails uDetails;
    
        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));
    
        return uDetails;
    }

    // Usamos el tipo de datos SET solo para usar otro diferente a List private
    Set < ? extends GrantedAuthority > getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        TipoUsuarioEnum userType = usuario.getTipoUsuario();

        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

        if (usuario.obtenerEntityId() != null)
        authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + usuario.obtenerEntityId()));
        
        return authorities;
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
    
        claims.put("userType", usuario.getTipoUsuario());
    
        if (usuario.obtenerEntityId() != null)
          claims.put("entityId", usuario.obtenerEntityId());
    
        return claims;
    }

    


    

    



    

    
}
