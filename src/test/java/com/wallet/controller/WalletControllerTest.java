package com.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.service.UserWalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WalletControllerTest {

    private static final Long ID = 1L;
    private static final Long USERS = 12345L;
    private static final Long WALLET = 123L;
    private static final String URL = "/user-wallet";

    @MockBean
    UserWalletService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testeSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(UserWallet.class))).willReturn(getMockUserWallet());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, USERS, WALLET))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.users").value(USERS))
                .andExpect(jsonPath("$.data.wallet").value(WALLET));
    }

    @Test
    public void testSaveInvalidUsers() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, null, WALLET))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Informar o id do usuário"));
    }

    private UserWallet getMockUserWallet() {
        UserWallet uw = new UserWallet();
        User u = new User();
        u.setId(USERS);

        Wallet w = new Wallet();
        w.setId(WALLET);

        uw.setId(ID);
        uw.setUsers(u);
        uw.setWallet(w);

        return uw;
    }

    private String getJsonPayload(Long id, Long users, Long wallet) throws JsonProcessingException {
        UserWalletDTO dto = new UserWalletDTO();
        dto.setId(id);
        dto.setUsers(users);
        dto.setWallet(wallet);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
