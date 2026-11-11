package br.edu.positivo.reservas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity 
public class Professor extends Usuario {
    @Column(nullable = false, unique = true)
    private String cpf;

    public Professor(){
        
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
