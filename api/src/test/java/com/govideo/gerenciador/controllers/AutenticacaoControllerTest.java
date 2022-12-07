package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.utilidades.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
public class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaDevolver200CasoOsDadosDeAutenticacaoEstejamCorretos() throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"admin@email.com\",\"senha\":\"123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveriaDevolver401CasoEmailParaAutenticacaoEstejaIncorreto() throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"invalido@email.com\",\"senha\":\"123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    public void deveriaDevolver401CasoSenhaParaAutenticacaoEstejaIncorreta() throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"admin@email.com\",\"senha\":\"abc\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    public void deveriaDevolver403CasoUsuarioEstejaInativo() throws Exception {
        TokenGenerator tokenGenerator = new TokenGenerator();

        String jsonCadastro = "{\r\n"
                + "    \"nome\": \"Usuario\",\r\n"
                + "    \"email\": \"usuario-inativo@email.com\",\r\n"
                + "    \"senha\": \"123\"\r\n"
                + "}";

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post("/usuarios")
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                        .content(jsonCadastro)
                                        .contentType(MediaType.APPLICATION_JSON));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String idUsuario = jsonParser.parseMap(resultString).get("id").toString();

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete("/usuarios/" + idUsuario)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)));

        URI uri = new URI("/login");
        String jsonLogin = "{\"email\":\"usuario-inativo@email.com\",\"senha\":\"123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jsonLogin).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

}
