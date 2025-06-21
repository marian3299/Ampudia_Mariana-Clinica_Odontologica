package com.example.cliniciaOdonto.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Schema(description = "Entity that represents a Paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique value per entity, auto-incremental")
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String cedula;
    @Column
    private LocalDate fechaIngreso;
    @OneToOne(cascade = CascadeType.ALL) //Cascade de tipo ALL --> Cualquier persistencia que realice con Pacientes va a impactar directamente en domicilio, esto lo hacemos porque la relacion es unidireccial (el paciente tiene un domicilio)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id") //referenceColumnName ->  vamos a referenciar cada domicilio con la columna id de Domicilio
    private Domicilio domicilio;

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, String cedula, LocalDate fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente(Long id, String nombre, String apellido, String cedula, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}
