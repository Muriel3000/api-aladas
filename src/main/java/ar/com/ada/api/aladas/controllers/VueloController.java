package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {
    
    //@Autowired 
    // Version nueva:
    private VueloService service;
    private AeropuertoService aeropService;

    public VueloController(VueloService service, AeropuertoService aeropService){
        this.service = service;
        this.aeropService = aeropService;
    }

    // // //

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> crearVuelo(@RequestBody Vuelo vuelo){
        
        GenericResponse respuesta = new GenericResponse();
       
        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);
       
        if(resultadoValidacion == ValidacionVueloDataEnum.OK){
            service.crear(vuelo);
            respuesta.isOk = true;
            respuesta.message = "Vuelo creado correctamente";
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";
            // toString, enviar el enum como respuesta para que front sepa que salio mal
            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    /*
     * @PostMapping("/api/v2/vuelos") 
     * public ResponseEntity<GenericResponse> postCrearVueloV2(@RequestBody Vuelo vuelo) { 
     * GenericResponse respuesta = new GenericResponse();
     * 
     * Aeropuerto ao = aeropuertoService.b
     * 
     * Vuelo vueloCreado = service.crear(vuelo.getFecha(), vuelo.getCapacidad(),
     * vuelo.getAeropuertoOrigen(), vuelo.getAeropuertoDestino(), vuelo.getPrecio(),
     * vuelo.getCodigoMoneda());
     * 
     * respuesta.isOk = true; respuesta.id = vueloCreado.getVueloId();
     * respuesta.message = "Vuelo creado correctamente";
     * 
     * return ResponseEntity.ok(respuesta); }
    */

    @PutMapping("/api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id, @RequestBody EstadoVueloRequest estadoVuelo){
        GenericResponse r = new GenericResponse();
        Vuelo vuelo = service.buscarPorId(id);
        vuelo.setEstadoVueloId(estadoVuelo.estado);
        service.actualizar(vuelo);
        r.isOk = true;
        r.message = "El estado del vuelo ha sido actualizado";
        r.id = vuelo.getVueloId();
        return ResponseEntity.ok(r);
    }

    @GetMapping("/api/vuelos/abiertos")
    public ResponseEntity<List<Vuelo>> getVuelosAbiertos(){
        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }
}
