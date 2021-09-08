package eu.opensource.portfolioclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PortfolioControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String url = "/portfolios";

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void viewPortfolioByIdTest() throws Exception {

        mvc.perform(get(url + "/{portfolioId}", 1))
           .andExpect(status().isOk())
           .andExpect(model().attribute("portfolio", hasProperty("name", equalTo("Portafoglio 1"))))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getFormCreatePortfolioTest() throws Exception {

        mvc.perform(get(url))
           .andExpect(status().isOk())
           .andExpect(model().attribute("portfolioForm", notNullValue()))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void createPortfolioTest() throws Exception {

        mvc.perform(post(url).param("name", "Porfolio X")
                             .param("cash", "100000"))
           .andExpect(flash().attribute("message", hasProperty("type", equalTo("success"))))
           .andExpect(redirectedUrl("/"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getFormUpdatePortfolioTest() throws Exception {

        mvc.perform(get(url + "/{portfolioId}", 1).param("form", "true"))
           .andExpect(model().attribute("portfolioForm", hasProperty("id", equalTo(1L))))
           .andExpect(model().attribute("portfolioForm", hasProperty("name", equalTo("Portafoglio 1"))))
           .andExpect(status().isOk());
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void updatePortfolio() throws Exception {

        mvc.perform(put(url).param("id", "1")
                            .param("name", "Porfolio X")
                            .param("cash", "100000"))
           .andExpect(flash().attribute("message", hasProperty("type", equalTo("success"))))
           .andExpect(redirectedUrl("/"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getFormAddToolTest() throws Exception {

        mvc.perform(get(url + "/{portfolioId}/tools", 2))
           .andExpect(model().attribute("portfolioName", equalTo("Portafoglio 2")))
           .andExpect(model().attribute("productForm", hasProperty("portfolioId", equalTo(2L))))
           .andExpect(status().isOk())
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void saveNewToolTest() throws Exception {

        mvc.perform(((post(url + "/{portfolioId}/tools", 2)).param("isin", "IT0001278511")
                                                            .param("price", "1.25")
                                                            .param("quantity", "10")))
           .andExpect(redirectedUrl("/"));
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getFormAddToolToPortfolioTest() throws Exception {

        mvc.perform(get(url + "/portfolio/{isin}", "IT0005218968"))
           .andExpect(model().attribute("portfolios", hasSize(2)))
           .andExpect(model().attribute("productForm", hasProperty("name", equalTo("Cct-Eu Tv Eur6m+0,75% Fb24 Eur"))))
           .andExpect(view().name("portfolios/addToolToPorfolio"))
           .andExpect(status().isOk())
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void saveNewToolInPortfolioTest() throws Exception {

        mvc.perform(((post(url + "/portfolio/tools")).param("isin", "IT0001278511")
                                                     .param("price", "1.25")
                                                     .param("quantity", "10")
                                                     .param("portfolioId", "2")))
           .andExpect(flash().attribute("message", hasProperty("type", equalTo("success"))))
           .andExpect(redirectedUrl("/"));
    }
}
