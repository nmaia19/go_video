package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
