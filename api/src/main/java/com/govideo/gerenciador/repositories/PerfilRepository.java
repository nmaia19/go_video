package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

  boolean existsByPerfil(String perfil);

  Optional<Perfil> findByPerfil(String perfil);
}
