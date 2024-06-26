package com.example.cliniciaOdonto.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoDTO {
    private Long id;
    private PacienteDTO paciente;
    private OdontologoDTO odontologo;
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public PacienteDTO getPacienteDTO() {
        return paciente;
    }

    public void setPacienteDTO(PacienteDTO pacienteDTO) {
        this.paciente = pacienteDTO;
    }

    public OdontologoDTO getOdontologoDTO() {
        return odontologo;
    }

    public void setOdontologoDTO(OdontologoDTO odontologoDTO) {
        this.odontologo = odontologoDTO;
    }
}
