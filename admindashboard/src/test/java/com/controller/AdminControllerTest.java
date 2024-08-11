package com.controller;

import com.SimpleApplication;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SimpleApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/v1/admin/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"123456\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Giả sử token được trả về trong body dạng JSON {"token": "jwt-token"}
        token = new JSONObject(response).getString("token");
    }

    @Test
    void testGetAllDataFromTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/admin")
                        .header("Authorization", "Bearer " + token)
                        .param("itemsPerPage", "10")
                        .param("page", "1")
                        .param("searchValue", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void testGetDataFromTableAndId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/admin/8ed5cc07-b618-4c37-8288-0b6d640ebdda")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

//    @Test
//    void testCreateData() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/create/someTable")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"key\": \"value\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().string("OK"))
//                .andDo(print());
//    }

    @Test
    void testVerifyAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/verify")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    void testUpdateData() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/update/someTable/1")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"action\": \"update\", \"key\": \"newValue\"}"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    void testDeleteData() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/update/someTable/1")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"action\": \"delete\"}"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    void testGetStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/admin/stats/video")
                        .header("Authorization", "Bearer " + token)
                        .param("start", "2023-01-01")
                        .param("end", "2023-01-31")
                        .param("filters", "someFilter"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}

