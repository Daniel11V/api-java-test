package com.coderhouse.controller;

import com.coderhouse.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - se ejecuta antes de todos los tests");
    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - se ejecuta antes de la ejecución de cada test");
    }

    @Test
    public void getMessagesString() throws Exception {
        var result = mockMvc.perform(get("/coder-house/mensajes/example"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Ejemplo de respuesta")));

    }

    @Test
    public void getMessagesByDescription() throws Exception {
        var result = mockMvc.perform(get("/coder-house/mensajes?description=Mensaje-ABCD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var messages = mapper.readValue(content, List.class);

        Assert.notNull(messages, "Lista de mensajes no nula");
        Assert.notEmpty(messages, "Lista de mensajes con elementos");
        Assert.isTrue(messages.size() == 3, "Tamaño de la lista es de 3");

    }

    @Test
    public void getAllMessages() throws Exception {
        var result = mockMvc.perform(get("/coder-house/mensajes/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var messages = mapper.readValue(content, List.class);

        Assert.notNull(messages, "Lista de mensajes no nula");
        Assert.notEmpty(messages, "Lista de mensajes con elementos");
        Assert.isTrue(messages.size() == 5, "Tamaño de la lista es de 4");

    }

    @Test
    public void getMessageById() throws Exception {
        mockMvc.perform(get("/coder-house/mensajes/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mensaje-ABCD")));

    }

    @Test
    public void getMessageByIdResult() throws Exception {
        var result = mockMvc.perform(get("/coder-house/mensajes/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var message = mapper.readValue(content, Message.class);

        Assert.notNull(message, "Mensaje no nula");
        Assert.isTrue(message.getId() == 1, "ID del mensaje OK");
        Assert.isTrue(message.getDescription().equals("Mensaje-ABCD"), "Descripción del mensaje OK");

    }
}
