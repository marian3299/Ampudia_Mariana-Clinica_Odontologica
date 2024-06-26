package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.dto.TurnoDTO;
import com.example.cliniciaOdonto.entity.Turno;
import com.example.cliniciaOdonto.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    public Turno registrsrTurno(Turno turno){
        return turnoRepository.save(turno);
    }

    public Optional<Turno> buscarPorId(Long id){
        return turnoRepository.findById(id);
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public List<TurnoDTO> listarTodos(){
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();
        for (Turno turno: turnos) {
            turnosDTO.add(turnoATurnoDTO(turno));
        }
        return turnosDTO;
    }

    public void actualizarTurno(Turno turno){
        turnoRepository.save(turno);
    }

    public TurnoDTO turnoATurnoDTO(Turno turno){
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setPacienteDTO(pacienteService.pacienteApacienteDTO(turno.getPaciente()));
        turnoDTO.setOdontologoDTO(odontologoService.odotologoAodontologoDTO(turno.getOdontologo()));
        turnoDTO.setFecha(turno.getFecha());
        return turnoDTO;
    }
}
