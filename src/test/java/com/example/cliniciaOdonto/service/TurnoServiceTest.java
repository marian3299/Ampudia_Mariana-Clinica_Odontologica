package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.entity.Turno;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void agregarTurno(){
        Optional<Paciente> paciente = pacienteService.buscarPorID(1L);
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(2L);
        Turno turno = new Turno(paciente.get(),odontologo.get(), LocalDateTime.of(2024,1,1,16,0));
        Turno turnoGuardado = turnoService.registrsrTurno(turno);
        Assertions.assertEquals(3L,turnoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPorId(){
        Optional<Turno> turno = turnoService.buscarPorId(3L);
        Assertions.assertNotNull(turno.get());
    }

    @Test
    @Order(3)
    public void buscarTodos(){
        List<Turno> turnos = turnoService.listarTodos();
        Assertions.assertEquals(3, turnos.size());
    }

    @Test
    @Order(4)
    public void actualizarTurno(){
        Optional<Paciente> paciente = pacienteService.buscarPorID(2L);
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(2L);
        Turno turno = new Turno(3L, paciente.get(),odontologo.get(), LocalDateTime.of(2024,1,1,16,0));
        turnoService.actualizarTurno(turno);
        Optional<Turno> turnoActualizado = turnoService.buscarPorId(3L);
        Assertions.assertEquals(2L,turnoActualizado.get().getPaciente().getId());
    }

    @Test
    @Order(5)
    public void eliminarTurno(){
        turnoService.eliminarTurno(3L);
        Optional<Turno> turnoEliminado = turnoService.buscarPorId(3L);
        Assertions.assertFalse(turnoEliminado.isPresent());
    }
}
