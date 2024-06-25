package com.example.cliniciaOdonto.security;

import com.example.cliniciaOdonto.entity.*;
import com.example.cliniciaOdonto.repository.OdontologoRepository;
import com.example.cliniciaOdonto.repository.PacienteRepository;
import com.example.cliniciaOdonto.repository.TurnoRepository;
import com.example.cliniciaOdonto.repository.UsuarioRepository;
import com.example.cliniciaOdonto.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Odontologo odontologo =  new Odontologo(1234, "Valentina", "Ceballos");
        Odontologo odontologo2 =  new Odontologo(5634, "Mara", "Vazquez");

        odontologoRepository.save(odontologo);
        odontologoRepository.save(odontologo2);

        odontologoRepository.findAll();

        Paciente paciente = new Paciente("Mariana", "Ampudia", "AUGM990818MD", LocalDate.of(2024,1,1), new Domicilio("Algarrobo",36,"Club de Golf la Ceiba","Merida"));
        Paciente paciente2 = new Paciente("Andres", "Cardenas", "AECE000607MD", LocalDate.of(2024,5,15), new Domicilio("Caoba",130,"Cholul","Merida"));

        pacienteRepository.save(paciente);
        pacienteRepository.save(paciente2);

        Turno turno = new Turno(paciente,odontologo, LocalDateTime.of(2024, 1,1, 18,0));
        Turno turno2 = new Turno(paciente2,odontologo2, LocalDateTime.of(2024, 5,10, 9,30));

        turnoRepository.save(turno);
        turnoRepository.save(turno2);

        String passSinCifrar = "user";
        String passCifrado = passwordEncoder.encode(passSinCifrar);
        Usuario usuario = new Usuario("mariana", "mariana3299", "user@user.com", passCifrado, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario);

        String passSinCifrarAdmin = "admin";
        String passCifradoAdmin =  passwordEncoder.encode(passSinCifrarAdmin);
        Usuario usuario1 = new Usuario("andres", "andres3299", "admin@admin.com", passCifradoAdmin, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario1);
    }
}
