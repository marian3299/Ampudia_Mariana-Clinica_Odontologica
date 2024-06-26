package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.entity.Odontologo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontolgoServieTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo(){
        Odontologo odontologo = new Odontologo(123, "Mariana", "Ampudia");
        Odontologo odotologoGuardado = odontologoService.guardarOdontologo(odontologo);
        Assertions.assertEquals(3L, odotologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorID(){
        Long id = 3L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        Assertions.assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarTodos(){
        List<Odontologo> odontologos = odontologoService.listarTodos();
        Assertions.assertEquals(3, odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologo(){
        Long id = 3L;
        Odontologo odontologo = new Odontologo(id, 123, "Mauricio", "Ampudia");
        odontologoService.actualizarOdontologo(odontologo);
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        Assertions.assertEquals("Mauricio", odontologoBuscado.get().getNombre());
    }

    @Test
    @Order(5)
    public void eliminarOdontologo(){
        Long id = 3L;
        odontologoService.eliminarOdontologo(id);
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarPorId(id);
        Assertions.assertFalse(odontologoEliminado.isPresent());
    }
}
