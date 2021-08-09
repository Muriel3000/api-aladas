package ar.com.ada.api.aladas.aladas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@SpringBootTest
class AladasApplicationTests {

	@Autowired
	VueloService vueloService;


	@Test
	void vueloTestPrecioNegativo() {
		Vuelo vueloConPrecioNegativo = new Vuelo();
		vueloConPrecioNegativo.setPrecio(new BigDecimal(-100));

		assertFalse(vueloService.validarPrecio(vueloConPrecioNegativo));
	}

	@Test
	void vueloTestOk(){
		Vuelo vueloConPrecioOk = new Vuelo();
		vueloConPrecioOk.setPrecio(new BigDecimal(100));
		assertTrue(vueloService.validarPrecio(vueloConPrecioOk));
	}

	@Test
	void vueloValidarVueloMismoDestinoUsandoGeneral(){
		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(8000));
		vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
		vuelo.setAeropuertoOrigen(2);
		vuelo.setAeropuertoDestino(2);

		assertTrue(vueloService.validarAeropuertoOrigenDifDestino(vuelo));
	}

	@Test
	void vueloValidarValidaciones(){
		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(20));
		vuelo.setAeropuertoOrigen(2);
		vuelo.setAeropuertoDestino(2);

		assertTrue(vueloService.validar(vuelo) == ValidacionVueloDataEnum.OK);
	}

}
