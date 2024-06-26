package com.example.cliniciaOdonto.controller;

import com.example.cliniciaOdonto.dto.PacienteDTO;
import com.example.cliniciaOdonto.dto.TurnoDTO;
import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.entity.Turno;
import com.example.cliniciaOdonto.exception.BadRequestException;
import com.example.cliniciaOdonto.exception.ResourceNotFoundException;
import com.example.cliniciaOdonto.service.OdontologoService;
import com.example.cliniciaOdonto.service.PacienteService;
import com.example.cliniciaOdonto.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class
TurnoController {
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> paciente = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if(paciente.isPresent() && odontologo.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            return ResponseEntity.ok(turnoService.registrsrTurno(turno));
        }else {
            throw new BadRequestException("Paciente u odontologo no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTodos(){
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);

        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado con exito");
        }else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Turno>> buscarPorId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);

        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado);
        }else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turnoActualizado) throws BadRequestException{
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(turnoActualizado.getId());

        if(turnoBuscado.isPresent()){
            Turno turno = turnoBuscado.get();
            turno.setOdontologo(turnoActualizado.getOdontologo());
            turno.setPaciente(turnoActualizado.getPaciente());
            turno.setFecha(turnoActualizado.getFecha());
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado con exito");
        }
        throw new BadRequestException("Datos incorrectos");
    }
}
