package com.example.cliniciaOdonto.service;

import com.example.cliniciaOdonto.dto.OdontologoDTO;
import com.example.cliniciaOdonto.entity.Odontologo;
import com.example.cliniciaOdonto.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    public List<Odontologo> listarTodos(){
        return odontologoRepository.findAll();
    }

    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);
    }

    public void eliminarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }

    public void actualizarOdontologo(Odontologo odontologo){
        odontologoRepository.save(odontologo);
    }

    public OdontologoDTO odotologoAodontologoDTO(Odontologo odontologo){
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(odontologo.getId());
        odontologoDTO.setNombre(odontologo.getNombre());
        odontologoDTO.setApellido(odontologo.getApellido());
        return odontologoDTO;
    }


}
