package eu.opensource.portfolioclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PortfolioControllerTest {

    @Autowired
    private MockMvc mvc;

    private String url = "/portfolios";

    @Test
    void viewPortfolioById() throws Exception {

        mvc.perform(get(url + "/{portfolioId}", 1))
           .andExpect(status().isOk());
    }
}