package com.govideo.gerenciador.utilidades;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

public class EmprestimoGenerator {

    public String cadastrarEmprestimo(MockMvc mockMvc, EquipamentosGenerator equipamentosGenerator, TokenGenerator tokenGenerator) throws Exception {
        String id = equipamentosGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);
        URI uri = new URI("/emprestimos/"+id);

        ResultActions result =
            mockMvc.
                    perform(
                            MockMvcRequestBuilders
                                    .post(uri)
                                    .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                    .contentType(MediaType.APPLICATION_JSON));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("id").toString();
    }
}
