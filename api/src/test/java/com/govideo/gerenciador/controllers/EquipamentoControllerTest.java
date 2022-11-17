package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.services.EquipamentoService;
import com.govideo.gerenciador.utilidades.EquipamentosGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipamentoControllerTest {
    //TODO: adicionar a linha abaixo depois de security pronto
    //private TokenGenerator tokenGenerator;

    private EquipamentosGenerator equipamentosGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EquipamentoService equipamentoService;

    @BeforeEach
    public void beforeEach() {
        this.equipamentosGenerator = new EquipamentosGenerator();
        //TODO: adicionar a linha abaixo depois de security pronto
        //tokenGenerator = new TokenGenerator();
    }

    @Test
    public void deveriaDevolver201AoCadastrarEquipamento() throws Exception {
        URI uri = new URI("/equipamentos");
        String json = "{\r\n"
                + "    \"modelo\": \"Pocket Cinema 6K\",\r\n"
                + "    \"descricao\": \"Filmadora profissional Pocket Cinema 6K\",\r\n"
                + "    \"marca\": \"Black Magic\",\r\n"
                + "    \"categoria\": \"Filmadoras\",\r\n"
                + "    \"urlFoto\": \"https://emania.vteximg.com.br/arquivos/ids/209607\"\r\n"
                + "}";

        //TODO: incluir header quando security pronto
        //.header("Authorization", "Bearer " + generator.obterTokenAdmin(mockMvc))
        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void deveriaDevolver200AoBuscarTodosOsEquipamentos() throws Exception {
        URI uri = new URI("/equipamentos");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        ResultActions result =
                //TODO: incluir header quando security pronto
                //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEquipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEquipamento.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEquipamentoPorId() throws Exception {
        URI uri = new URI("/equipamentos/1");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        ResultActions result =
                mockMvc.
                        //TODO: incluir header quando security pronto
                        //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String equipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(equipamento.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEquipamentoPorStatusDisponivel() throws Exception {
        URI uri = new URI("/equipamentos/buscarPorStatus/disponivel");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        ResultActions result =
                mockMvc.
                        //TODO: incluir header quando security pronto
                        //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String equipamento = result.andReturn().getResponse().getContentAsString();
        assertFalse(equipamento.isEmpty());
    }

    //TODO: testar buscar por status caso inativo e indisponível

    @Test
    public void deveriaDevolver200AoAtualizarEquipamento() throws Exception {
        URI uri = new URI("/equipamentos/1");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        String json = "{\r\n"
                + "    \"modelo\": \"Modelo alterado\",\r\n"
                + "    \"descricao\": \"Descricao alterada\",\r\n"
                + "    \"marca\": \"Marca alterada\",\r\n"
                + "    \"categoria\": \"Categoria alterada\",\r\n"
                + "    \"urlFoto\": \"url alterada\"\r\n"
                + "}";

        mockMvc.
                perform(
                        //TODO: incluir header quando security pronto
                        //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc
                        MockMvcRequestBuilders
                                .put(uri)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }


    //TODO: verificar mensagem que vem com a resposta
    @Test
    public void deveriaDevolver200AoExcluirEquipamentos() throws Exception {
        URI uri = new URI("/equipamentos/1");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        mockMvc.
                //TODO: incluir header quando security pronto
                //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc
                perform(
                        MockMvcRequestBuilders
                                .delete(uri))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    //TODO: testar quando (1) está disponível, mas já teve empréstimo--> inativa e (2) está em empréstimo --> faz nada

}
