package com.example.cliniciaOdonto.controller;

import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.exception.BadRequestException;
import com.example.cliniciaOdonto.exception.ResourceNotFoundException;
import com.example.cliniciaOdonto.service.OdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
@Tag(name = "Controller de Odontologos", description = "Permite registar, eliminar, listar y actualizar")
public class OdontologoController {
    private static final String VALID_REQUEST = "{\n" +
            "  \"nombre\": \"Juan\",\n" +
            "  \"apellido\": \"Perez\",\n" +
            "  \"matricula\": 12345\n" +
            "}";

    @Autowired
    OdontologoService odontologoService;
    @Operation(summary = "Get all Odontologos")
    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    @Operation(summary = "Get a Odontologo by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo founded",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Odontologo.class))}),
            @ApiResponse(responseCode = "400", description = "Odontologo not found, invalid id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);

        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado);
        }
        throw new ResourceNotFoundException("Odontologo no encontrado");
    }

    @PostMapping
    @Operation(summary = "Register Odontologo", description = "Send body without id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {@ExampleObject(value = VALID_REQUEST)}))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Odontologo.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Odontologo not found, invalid id", content = @Content)
    })
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
    @Operation(summary = "Update a Odontologo", description = "Send Odontologo with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologo.getId());

        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontologo actualizado con exito");
        }
        throw new BadRequestException("Datos incorrectos");
    }
}
