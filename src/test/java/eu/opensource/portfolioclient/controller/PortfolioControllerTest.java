package eu.opensource.portfolioclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PortfolioControllerTest {

    @Autowired
    private MockMvc mvc;

    private String url = "/portfolios";

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void viewPortfolioById() throws Exception {

        mvc.perform(get(url + "/{portfolioId}", 1))
           .andExpect(status().isOk())
           .andExpect(model().attribute("portfolio", hasProperty("name", equalTo("Portafoglio 1"))))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getCreateFormPortfolioTest() throws Exception {

        mvc.perform(get(url))
           .andExpect(status().isOk())
           .andExpect(model().attribute("portfolioForm", notNullValue()))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void createPortfolioTest() throws Exception {

        mvc.perform(post(url).param("name", "Portfolio X")
                             .param("cash", "50000.0"))
           .andExpect(status().is3xxRedirection())
        ;
    }
}