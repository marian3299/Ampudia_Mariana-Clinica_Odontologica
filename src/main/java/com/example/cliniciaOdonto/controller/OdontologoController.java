package com.example.cliniciaOdonto.controller;

import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.exception.BadRequestException;
import com.example.cliniciaOdonto.exception.ResourceNotFoundException;
import com.example.cliniciaOdonto.service.OdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
@Tag(name = "Controller de Odontologos", description = "permite registar, eliminar, listar y actualizar")
public class OdontologoController {
    @Autowired
    OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);

        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado);
        }
        throw new ResourceNotFoundException("Odontologo no encontrado");
    }

    @PostMapping
    @Operation(summary = "nos permite registrar", description = "enviar odontologo sin id")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable("id") Long id) throws ResourceNotFoundException {

        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);

        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado con exito");
        }else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }


    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologo.getId());

        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontologo actualizado con exito");
        }
        throw new BadRequestException("Datos incorrectos");
    }
}
