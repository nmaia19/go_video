package com.govideo.gerenciador.repositories;

import com.govideo.gerenciador.entities.Emprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    //@Query("SELECT emp, eq, usr FROM Emprestimo emp, Equipamento eq, Usuario usr WHERE emp.dataFim IS NOT NULL AND emp.equipamento = eq AND emp.usuario = usr")
    //Page<Emprestimo> findEmprestimosEncerrados(Pageable paginacao);
    Page<Emprestimo> findByDataFimIsNotNull(Pageable paginacao);

}
