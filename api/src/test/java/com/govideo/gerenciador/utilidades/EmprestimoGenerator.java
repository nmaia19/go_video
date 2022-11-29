package com.govideo.gerenciador.utilidades;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

public class EmprestimoGenerator {

    public void cadastrarEmprestimo(MockMvc mockMvc, EquipamentosGenerator equipamentosGenerator, TokenGenerator tokenGenerator) throws Exception {
        URI uri = new URI("/emprestimos/1");
        equipamentosGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);
        tokenGenerator.cadastrarColaborador(mockMvc);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .contentType(MediaType.APPLICATION_JSON));
    }
}
