package br.com.alura.forum.controller;

import br.com.alura.forum.controller.form.LoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AutenticacaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void deveriaDevolver400EmDadosIncorretos() throws Exception {

        URI uri = new URI("/auth");

        LoginForm form = new LoginForm();
        form.setEmail("eduardo@gmail.com");
        form.setSenha("senha");

        String json = mapper.writeValueAsString(form);

        mockMvc.perform(post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}