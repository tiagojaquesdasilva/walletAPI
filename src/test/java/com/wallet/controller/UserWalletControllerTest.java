package com.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.WalletDTO;
import com.wallet.entity.Wallet;
import com.wallet.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserWalletControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "User Test";
    private static final BigDecimal VALUE = BigDecimal.valueOf(254000);
    private static final String URL = "/wallet";

    @MockBean
    WalletService service;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser
    public void testeSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(Wallet.class))).willReturn(getMockWallet());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, VALUE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.value").value(VALUE))
                .andExpect(jsonPath("$.data.name").value(NAME));
    }

    @Test
    @WithMockUser
    public void testSaveInvalidName() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, null, VALUE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("O nome não pode ser nulo"));
    }

    private Wallet getMockWallet() {
        Wallet w = new Wallet();
        w.setId(ID);
        w.setName(NAME);
        w.setValue(VALUE);

        return w;
    }

    private String getJsonPayload(Long id, String name, BigDecimal value) throws JsonProcessingException {
        WalletDTO dto = new WalletDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setValue(value);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
