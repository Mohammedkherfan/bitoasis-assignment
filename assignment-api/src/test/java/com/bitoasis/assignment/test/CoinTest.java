package com.bitoasis.assignment.test;

import com.bitoasis.assignment.AssignmentApplication;
import com.bitoasis.assignment.configuration.CoinConfiguration;
import com.bitoasis.assignment.configuration.SecurityConfiguration;
import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.Sort;
import com.bitoasis.assignment.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AssignmentApplication.class, CoinConfiguration.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
public class CoinTest {

    @Autowired
    private MockMvc mvc;

    private TestUtil jacksonUtil = new TestUtil();

    @Test
    public void whenFindAllCoins_AndSortingParamIsAsc() throws Exception {
        ResultActions action = mvc.perform(get("/v1/coins"+ "?sort="+ Sort.ASC)
                .contentType(MediaType.APPLICATION_JSON));
        List<CoinDto> coins = (List<CoinDto>) jacksonUtil.fromJson(action.andReturn().getResponse().getContentAsString(), List.class);
        Assert.assertTrue(!coins.isEmpty());
        action
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindAllCoins_AndSortingParamIsDesc() throws Exception {
        ResultActions action = mvc.perform(get("/v1/coins"+ "?sort="+ Sort.DESC)
                .contentType(MediaType.APPLICATION_JSON));
        List<CoinDto> coins = (List<CoinDto>) jacksonUtil.fromJson(action.andReturn().getResponse().getContentAsString(), List.class);
        Assert.assertTrue(!coins.isEmpty());
        action
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindAllCoins_AndSortingParamIsNull() throws Exception {
        ResultActions action = mvc.perform(get("/v1/coins")
                .contentType(MediaType.APPLICATION_JSON));
        List<CoinDto> coins = (List<CoinDto>) jacksonUtil.fromJson(action.andReturn().getResponse().getContentAsString(), List.class);
        Assert.assertTrue(!coins.isEmpty());
        action
                .andDo(print())
                .andExpect(status().isOk());
    }
}
