package com.govideo.gerenciador.utilidades;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

public class EmprestimoGenerator {

    //TODO: incluir tokenGenerator quando security pronto
    public void cadastrarEmprestimo(MockMvc mockMvc, EquipamentosGenerator equipamentosGenerator) throws Exception {
        URI uri = new URI("/emprestimos/1");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        //TODO: incluir header quando security pronto
        //.header("Authorization", "Bearer " + generator.obterTokenAdmin(mockMvc))
        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .contentType(MediaType.APPLICATION_JSON));
    }
}
