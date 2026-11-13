package br.edu.positivo.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.positivo.reservas.entity.Professor;

public interface ProfessorRepository extends JpaRepository <Professor, Long> {

}
