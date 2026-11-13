package br.edu.positivo.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.positivo.reservas.entity.Sala;

public interface SalaRepository extends JpaRepository <Sala, Long> {

}
