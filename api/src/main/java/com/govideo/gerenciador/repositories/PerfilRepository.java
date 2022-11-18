package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

  boolean existsByPerfil(String perfil);

}
