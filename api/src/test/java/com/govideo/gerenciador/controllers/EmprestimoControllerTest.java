package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.services.EmprestimoService;
import com.govideo.gerenciador.utilidades.EmprestimoGenerator;
import com.govideo.gerenciador.utilidades.EquipamentoGenerator;
import com.govideo.gerenciador.utilidades.TokenGenerator;
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

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class EmprestimoControllerTest {

    private TokenGenerator tokenGenerator;

    private EquipamentoGenerator equipamentoGenerator;

    private EmprestimoGenerator emprestimoGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmprestimoService emprestimoService;

    @BeforeEach
    public void beforeEach() {
        this.equipamentoGenerator = new EquipamentoGenerator();
        this.emprestimoGenerator = new EmprestimoGenerator();
        this.tokenGenerator = new TokenGenerator();
    }

    @Test
    public void deveriaDevolver200AoBuscarTodosOsEmprestimos() throws Exception {
        URI uri = new URI("/emprestimos");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    public void deveriaDevolver201AoCadastrarEmprestimo() throws Exception {
        String idEquipamento = equipamentoGenerator.cadastrarEquipamento(mockMvc, tokenGenerator);
        URI uri = new URI("/emprestimos/" + idEquipamento);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimoPorId() throws Exception {
        String idEmprestimo = emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);
        URI uri = new URI("/emprestimos/" + idEmprestimo);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String emprestimo = result.andReturn().getResponse().getContentAsString();
        assertFalse(emprestimo.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimosPorStatusVigentes() throws Exception {
        URI uri = new URI("/emprestimos/vigentes");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimosPorUsuario() throws Exception {
        URI uri = new URI("/emprestimos/usuario/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimosEncerradosPorUsuario() throws Exception {
        URI uri = new URI("/emprestimos/encerrados/usuario/1");
        String idEmprestimo = emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put("/emprestimos/encerrar/" + idEmprestimo)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)));

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimosVigentesPorUsuario() throws Exception {
        URI uri = new URI("/emprestimos/vigentes/usuario/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoEncerrarEmprestimo() throws Exception {
        String idEmprestimo = emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);
        URI uri = new URI("/emprestimos/encerrar/" + idEmprestimo);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver200AoBuscarEmprestimosEncerrados() throws Exception {
        URI uri = new URI("/emprestimos/encerrados");
        String idEmprestimo = emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put("/emprestimos/encerrar/" + idEmprestimo)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)));

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.contains("\"numberOfElements\":0"));
    }

}
