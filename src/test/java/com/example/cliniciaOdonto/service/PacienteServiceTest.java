package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.entity.Domicilio;
import com.example.cliniciaOdonto.entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Orden de acuerdo a las anotaciones
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;
    @Test
    @Order(1)
    public void guardarPaciente(){
        Paciente paciente = new Paciente("Jorgito", "Pereyra", "11111", LocalDate.of(2024,6,20), new Domicilio("alga",123, "ceiba", "Merida"));
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        Assertions.assertEquals(3L,pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPacientePorID(){
        Long id = 3L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        Assertions.assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void actualizarPaciente(){
        Long id = 3L;
        Paciente paciente = new Paciente(id,"German", "Fraire", "11111", LocalDate.of(2024,6,20), new Domicilio("alga",123, "ceiba", "Merida"));
        pacienteService.actualizarPaciente(paciente);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        Assertions.assertEquals("German", pacienteBuscado.get().getNombre());
    }

    @Test
    @Order(4)
    public void lisarTodos(){
        List<Paciente> pacientes = pacienteService.buscarTodos();
        Assertions.assertEquals(3, pacientes.size());
    }

    @Test
    @Order(5)
    public void eliminarPaciente(){
        pacienteService.eliminarPaciente(3L);
        Optional<Paciente> pacienteEliminado = pacienteService.buscarPorID(3L);
        Assertions.assertFalse(pacienteEliminado.isPresent());
    }
}
