package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.entity.Paciente;
import com.example.cliniciaOdonto.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }

    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }
}
