package com.govideo.gerenciador.utilidades;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

public class EquipamentosGenerator {

    public void cadastrarEquipamento(MockMvc mockMvc, TokenGenerator tokenGenerator) throws Exception {
        URI uri = new URI("/equipamentos");
        String json = "{\r\n"
                + "    \"modelo\": \"Pocket Cinema 6K\",\r\n"
                + "    \"descricao\": \"Filmadora profissional Pocket Cinema 6K\",\r\n"
                + "    \"marca\": \"Black Magic\",\r\n"
                + "    \"categoria\": \"Filmadoras\",\r\n"
                + "    \"urlFoto\": \"https://emania.vteximg.com.br/arquivos/ids/209607\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON));
    }
}
