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
class CatalogAdminControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String url = "/admin/catalog";

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getProductsByCategoryTest() throws Exception {

        mvc.perform(get(url + "/{toolId}/products", 1))
           .andExpect(status().isOk())
           .andExpect(model().attribute("products", hasProperty("content", hasSize(10))))
           .andExpect(view().name("catalog/productsListAdmin"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getCreateProductFormTest() throws Exception {

        mvc.perform(get(url + "/tools/products"))
           .andExpect(status().isOk())
           .andExpect(model().attribute("tools", hasSize(5)))
           .andExpect(model().attribute("productForm", notNullValue()))
           .andExpect(view().name("catalog/createProduct"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void createProductTest() throws Exception {

        mvc.perform(post(url + "/tools/products").param("isin", "IT0005449969")
                                                 .param("name", "Btp Tf 0,95% Dc31 Eur")
                                                 .param("toolId", "4"))
           .andExpect(flash().attribute("message", hasProperty("type", equalTo("success"))))
           .andExpect(redirectedUrl("/"));
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void getUpdaProductForm() throws Exception {

        mvc.perform(get(url + "/tools/{isin}", "IT0001233417"))
           .andExpect(status().isOk())
           .andExpect(model().attribute("tools", hasSize(5)))
           .andExpect(model().attribute("productForm", hasProperty("name", equalTo("A2A"))))
           .andExpect(view().name("catalog/editProduct"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void updateProduct() throws Exception {

        mvc.perform(put(url + "/tools/products").param("id", "1")
                                                .param("isin", "IT0001233417")
                                                .param("name", "A2A")
                                                .param("toolId", "1"))
           .andExpect(flash().attribute("message", hasProperty("type", equalTo("success"))))
           .andExpect(redirectedUrl("/"))
        ;
    }

    @Sql({"/schema-h2.sql", "/data-h2.sql"})
    @Test
    void deleteProduct() throws Exception {

        mvc.perform(delete(url + "/tools/products").param("isin", "IT0001233417"))
           .andExpect(redirectedUrl("/"))
        ;
    }
}