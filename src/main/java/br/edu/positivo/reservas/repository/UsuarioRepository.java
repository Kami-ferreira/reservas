package br.edu.positivo.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.positivo.reservas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

}
