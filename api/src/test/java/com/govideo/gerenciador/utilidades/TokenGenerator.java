package com.govideo.gerenciador.utilidades;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

public class TokenGenerator {

    public String obterTokenAdmin(MockMvc mockMvc) throws Exception {
        URI uri = new URI("/login");
        String json = "{\"email\":\"admin@email.com\",\"senha\":\"123\"}";

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post(uri)
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }

    public String obterTokenColaborador(MockMvc mockMvc) throws Exception {

        URI uri = new URI("/login");
        String json = "{\"email\":\"colaborador@email.com\",\"senha\":\"123\"}";

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post(uri)
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }
}
