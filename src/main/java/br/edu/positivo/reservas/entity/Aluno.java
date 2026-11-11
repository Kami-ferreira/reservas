package br.edu.positivo.reservas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity 
public class Aluno extends Usuario {
    @Column(nullable = false, unique = true)
    private String rgm;

    public Aluno() {

    }

    public String getRgm() {
        return rgm;
    }

    public void setRgm(String rgm) {
        this.rgm = rgm;
    }
}
