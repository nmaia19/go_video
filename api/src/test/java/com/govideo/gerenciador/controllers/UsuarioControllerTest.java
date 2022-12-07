package com.govideo.gerenciador.controllers;

import com.govideo.gerenciador.services.UsuarioService;
import com.govideo.gerenciador.utilidades.EmprestimoGenerator;
import com.govideo.gerenciador.utilidades.EquipamentoGenerator;
import com.govideo.gerenciador.utilidades.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    private TokenGenerator tokenGenerator;

    private EquipamentoGenerator equipamentoGenerator;

    private EmprestimoGenerator emprestimoGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    public void beforeEach() {
        this.equipamentoGenerator = new EquipamentoGenerator();
        this.emprestimoGenerator = new EmprestimoGenerator();
        this.tokenGenerator = new TokenGenerator();
    }

    @Test
    public void deveriaDevolver201AoCadastrarUsuario() throws Exception {
        URI uri = new URI("/usuarios");
        String json = "{\r\n"
                + "    \"nome\": \"Usuario\",\r\n"
                + "    \"email\": \"usuario-cadastrar@email.com\",\r\n"
                + "    \"senha\": \"123\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void deveriaDevolver200AoBuscarTodosOsUsuarios() throws Exception {
        URI uri = new URI("/usuarios");

        ResultActions result =
            mockMvc.
                    perform(
                            MockMvcRequestBuilders
                                    .get(uri)
                                    .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                    .andExpect(MockMvcResultMatchers
                            .status()
                            .is(200));

        String listaUsuario = result.andReturn().getResponse().getContentAsString();
        assertFalse(listaUsuario.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarUsuarioPorId() throws Exception {
        URI uri = new URI("/usuarios/2");

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String usuario = result.andReturn().getResponse().getContentAsString();
        assertFalse(usuario.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoBuscarUsuariosPorStatusAtivo() throws Exception {
        URI uri = new URI("/usuarios/buscarPorStatus/ativo");

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .get(uri)
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                        .andExpect(MockMvcResultMatchers
                                .status()
                                .is(200));

        String usuario = result.andReturn().getResponse().getContentAsString();
        assertFalse(usuario.isEmpty());
    }

    @Test
    public void deveriaDevolver200AoResetarSenha() throws Exception {
        String json = "{\r\n"
                + "    \"nome\": \"Usuario\",\r\n"
                + "    \"email\": \"usuario-resetar-senha@email.com\",\r\n"
                + "    \"senha\": \"123\"\r\n"
                + "}";

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post("/usuarios")
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String idUsuario = jsonParser.parseMap(resultString).get("id").toString();

        URI uri = new URI("/usuarios/resetarSenha/" + idUsuario);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver200AoAlterarSenha() throws Exception {
        URI uri = new URI("/usuarios/alterarSenha/2");
        String json = "{\r\n"
                + "    \"senhaAntiga\": \"123\",\r\n"
                + "    \"novaSenha\": \"321\",\r\n"
                + "    \"confirmaNovaSenha\": \"321\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver500AoAlterarSenhaComAntigaIncorreta() throws Exception {
        URI uri = new URI("/usuarios/alterarSenha/2");
        String json = "{\r\n"
                + "    \"senhaAntiga\": \"-\",\r\n"
                + "    \"novaSenha\": \"321\",\r\n"
                + "    \"confirmaNovaSenha\": \"321\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(500));
    }

    @Test
    public void deveriaDevolver500AoAlterarSenhaComAntigaIgualANova() throws Exception {
        URI uri = new URI("/usuarios/alterarSenha/2");
        String json = "{\r\n"
                + "    \"senhaAntiga\": \"123\",\r\n"
                + "    \"novaSenha\": \"123\",\r\n"
                + "    \"confirmaNovaSenha\": \"123\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(500));
    }

    @Test
    public void deveriaDevolver500AoAlterarSenhaComNovaDiferenteDaConfirmacao() throws Exception {
        URI uri = new URI("/usuarios/alterarSenha/2");

        String json = "{\r\n"
                + "    \"senhaAntiga\": \"123\",\r\n"
                + "    \"novaSenha\": \"321\",\r\n"
                + "    \"confirmaNovaSenha\": \"-\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenColaborador(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(500));
    }

    @Test
    public void deveriaDevolver200AoAtualizarUsuario() throws Exception {
        URI uri = new URI("/usuarios/alterarNome/2");
        String json = "{\r\n"
                + "    \"nome\": \"Nome alterado\"\r\n"
                + "}";

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver200AoInativarUsuario() throws Exception {
        String json = "{\r\n"
                + "    \"nome\": \"Usuario\",\r\n"
                + "    \"email\": \"usuario-inativar@email.com\",\r\n"
                + "    \"senha\": \"123\"\r\n"
                + "}";

        ResultActions result =
                mockMvc.
                        perform(
                                MockMvcRequestBuilders
                                        .post("/usuarios")
                                        .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc))
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String idUsuario = jsonParser.parseMap(resultString).get("id").toString();

        URI uri = new URI("/usuarios/" + idUsuario);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void deveriaDevolver403AoInativarUsuarioComEmprestimoVigente() throws Exception {
        URI uri = new URI("/usuarios/2");
        emprestimoGenerator.cadastrarEmprestimo(mockMvc, equipamentoGenerator, tokenGenerator);

        mockMvc.
                perform(
                        MockMvcRequestBuilders
                                .delete(uri)
                                .header("Authorization", "Bearer " + tokenGenerator.obterTokenAdmin(mockMvc)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(403));
    }

}
