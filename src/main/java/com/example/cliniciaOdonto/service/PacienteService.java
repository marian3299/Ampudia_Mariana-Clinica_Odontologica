package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.dto.PacienteDTO;
import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    //metodos manuales
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    public Optional<Paciente> buscarPorID(Long id){
        return pacienteRepository.findById(id);
    }

    public Optional<PacienteDTO> buscarPorIdDTO(Long id){
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);

        if(pacienteBuscado.isPresent()){
            return Optional.of(pacienteApacienteDTO(pacienteBuscado.get()));
        }else {
            return Optional.empty();
        }
    }

    public List<PacienteDTO> buscarTodosDTO(){
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            PacienteDTO pacienteDTO = pacienteApacienteDTO(paciente);
            pacientesDTO.add(pacienteDTO);
        }

        return pacientesDTO;
    }

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }

    public PacienteDTO pacienteApacienteDTO(Paciente paciente){
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNombre(paciente.getNombre());
        pacienteDTO.setApellido(paciente.getApellido());
        return pacienteDTO;
    }
}
