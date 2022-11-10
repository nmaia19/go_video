package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    Page<Equipamento> findByStatus(StatusEquipamento statusEquipamento, Pageable paginacao);

}
