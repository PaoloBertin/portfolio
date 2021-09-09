package eu.opensource.portfolioclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class WelcomeControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String url = "/";

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void viewWelcomeTest() throws Exception {

        mvc.perform(get(url))
           .andExpect(status().isOk())
           .andExpect(model().attribute("portfolios", hasSize(2)))
           .andExpect(view().name("welcome"))
        ;
    }
}
