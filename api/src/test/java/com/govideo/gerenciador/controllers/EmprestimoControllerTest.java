package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.services.EmprestimoService;
import com.govideo.gerenciador.utilidades.EmprestimoGenerator;
import com.govideo.gerenciador.utilidades.EquipamentosGenerator;

import org.junit.jupiter.api.*;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmprestimoControllerTest {
    //TODO: adicionar a linha abaixo depois de security pronto
    //private TokenGenerator tokenGenerator;

    private EmprestimoGenerator emprestimoGenerator;
    private EquipamentosGenerator equipamentosGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmprestimoService emprestimoService;

    @BeforeEach
    public void beforeEach() {
        this.equipamentosGenerator = new EquipamentosGenerator();
        this.emprestimoGenerator = new EmprestimoGenerator();
        //TODO: adicionar a linha abaixo depois de security pronto
        //tokenGenerator = new TokenGenerator();
    }

    @Test
    @Order(1)
    public void deveriaDevolver200AoBuscarTodosOsEmprestimos() throws Exception {
        URI uri = new URI("/emprestimos");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    @Order(2)
    public void deveriaDevolver201AoCadastrarEmprestimo() throws Exception {

        URI uri = new URI("/emprestimos/2");
        equipamentosGenerator.cadastrarEquipamento(mockMvc);

        //TODO: incluir header quando security pronto
        //.header("Authorization", "Bearer " + generator.obterTokenAdmin(mockMvc))
        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    @Order(3)
    public void deveriaDevolver200AoBuscarEmprestimoPorId() throws Exception {
        URI uri = new URI("/emprestimos/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String emprestimo = result.andReturn().getResponse().getContentAsString();
        assertFalse(emprestimo.isEmpty());
    }

    @Test
    @Order(4)
    public void deveriaDevolver200AoBuscarEmprestimosPorStatusVigentes() throws Exception {
        URI uri = new URI("/emprestimos/vigentes");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    @Order(5)
    public void deveriaDevolver200AoBuscarEmprestimosPorUsuario() throws Exception {
        URI uri = new URI("/emprestimos/usuario/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    @Order(6)
    public void deveriaDevolver200AoBuscarEmprestimosVigentesPorUsuario() throws Exception {
        URI uri = new URI("/emprestimos/vigentes/usuario/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaEmprestimos.isEmpty());
    }

    @Test
    @Order(7)
    public void deveriaDevolver200AoEncerrarEmprestimo() throws Exception {
        URI uri = new URI("/emprestimos/encerrar/1");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

        mockMvc.
                //TODO: incluir header quando security pronto
                //.header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc
                        perform(
                        MockMvcRequestBuilders
                                .put(uri))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    @Order(8)
    public void deveriaDevolver200AoBuscarEmprestimosEncerrados() throws Exception {
        URI uri = new URI("/emprestimos/encerrados");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentosGenerator);

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

        String listaEmprestimos = result.andReturn().getResponse().getContentAsString();

        assertFalse(listaEmprestimos.contains("\"numberOfElements\":0"));
    }
}
