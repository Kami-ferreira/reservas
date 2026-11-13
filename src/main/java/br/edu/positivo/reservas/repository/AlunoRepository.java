package br.edu.positivo.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.positivo.reservas.entity.Aluno;

public interface AlunoRepository extends JpaRepository <Aluno, Long> {

}
