package com.example.cliniciaOdonto.controller;

import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.exception.BadRequestException;
import com.example.cliniciaOdonto.exception.ResourceNotFoundException;
import com.example.cliniciaOdonto.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(paciente.getId());

        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Paciente actualizado con exito");
        }else {
            throw new BadRequestException("Datos incorrectos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);

        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Paciente eliminado con exito");
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}
