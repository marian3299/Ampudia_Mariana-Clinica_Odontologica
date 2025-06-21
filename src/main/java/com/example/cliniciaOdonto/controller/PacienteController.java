package com.example.cliniciaOdonto.controller;

import com.example.cliniciaOdonto.dto.PacienteDTO;
import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.exception.BadRequestException;
import com.example.cliniciaOdonto.exception.ResourceNotFoundException;
import com.example.cliniciaOdonto.service.PacienteService;
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
@RequestMapping("/pacientes")
@Tag(name = "Paciente controller", description = "Allows register, delete, list and update")
public class PacienteController {
    private static final String VALID_REQUEST = "{\n" +
            "  \"nombre\": \"Juan\",\n" +
            "  \"apellido\": \"Perez\",\n" +
            "  \"cedula\": \"ABC1234\",\n" +
            "  \"fechaIngreso\": \"2024-03-16\",\n" +
            "  \"domicilio\": {\n" +

            "}";
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @ResponseBody
    @Operation(summary = "Get all Pacientes")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Paciente by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente founded",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))}),
            @ApiResponse(responseCode = "400", description = "Paciente not found, invalid id", content = @Content)
    })
    public ResponseEntity<Optional<Paciente>> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @GetMapping("/pacienteDTO/{id}")
    @Operation(summary = "Get a PacienteDTO by its id", description = "A PacienteDTO only contains nombre, apellido and id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PacienteDTO founded",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "400", description = "PacienteDTO not found, invalid id", content = @Content)
    })
    public ResponseEntity<Optional<PacienteDTO>> buscarPorIdDTO(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<PacienteDTO> pacienteDTObuscado = pacienteService.buscarPorIdDTO(id);
        if(pacienteDTObuscado.isPresent()){
            return ResponseEntity.ok(pacienteDTObuscado);
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @GetMapping("/pacientesDTO")
    @Operation(summary = "Get all PacientesDTO")
    public ResponseEntity<List<PacienteDTO>> buscarTodosDTO(){
        return ResponseEntity.ok(pacienteService.buscarTodosDTO());
    }

    @PostMapping
    @Operation(summary = "Register Paciente", description = "Send body without id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {@ExampleObject(value = VALID_REQUEST)}))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
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
