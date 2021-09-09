package eu.opensource.portfolioclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class WatchListControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String url = "/watchlist";

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getWatchlistTest() throws Exception {

        mvc.perform(get(url + "/{watchlistId}", 1))
           .andExpect(model().attribute("watchlistName", equalTo("FTSE MIB")))
           .andExpect(model().attribute("watchlist", hasProperty("name", equalTo("FTSE MIB"))))
           .andExpect(view().name("watchlist/watchlist"))
           .andExpect(status().isOk());
    }
}