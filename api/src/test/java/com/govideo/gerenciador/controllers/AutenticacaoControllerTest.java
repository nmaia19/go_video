package com.govideo.gerenciador.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
    public void deveriaDevolver400CasoEmailParaAutenticacaoEstejaIncorreto() throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"invalido@email.com\",\"senha\":\"123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDevolver401CasoSenhaParaAutenticacaoEstejaIncorreta() throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"admin@email.com\",\"senha\":\"abc\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }
}
