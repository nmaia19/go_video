package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query(value = "SELECT * FROM emprestimo WHERE data_fim is null AND usuario_id = :idUsuario", nativeQuery = true)
    Page<Emprestimo> findPorUsuarioEStatus (Long idUsuario, Pageable paginacao);

    Page<Emprestimo> findByDataFimIsNotNull(Pageable paginacao);

    Page<Emprestimo> findByDataFimIsNull(Pageable paginacao);

    Page<Emprestimo> findByEquipamento(Equipamento equipamento, Pageable paginacao);

    Page<Emprestimo> findByUsuario(Usuario usuario, Pageable paginacao);

}
