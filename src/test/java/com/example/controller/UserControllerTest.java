package com.example.controller;

import com.example.common.http.PagingRequest;
import com.example.common.http.Response;
import com.example.controller.user.UserController;
import com.example.dto.user.UserResponse;
import com.example.entity.user.User;
import com.example.service.user.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@SuppressWarnings("ALL")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void getOneUser() throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("Nga")
                .address("Hanoi")
                .phone("01111")
                .build();
        Mockito.when(userService.findById(userResponse.getId()))
                .thenReturn(userResponse);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/webapi/v1/user/" + userResponse.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Response<?> userResponses = objectMapper.readValue(content,
                Response.class);
        assertEquals(200, userResponses.getCode());
        assertEquals(userResponse.getName(), ((LinkedHashMap) userResponses.getData()).get("name"));
    }

    @Test
    public void getAllUsers() throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("Nga")
                .address("Hanoi")
                .phone("01111")
                .build();
        String uri = "/webapi/v1/user/all";

        Mockito.when(userService.findAll())
                .thenReturn(Collections.singletonList(userResponse));

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Response<?> userResponses = objectMapper.readValue(content,
                Response.class);
        assertEquals(200, userResponses.getCode());
        assertFalse(((List<UserResponse>) userResponses.getData()).isEmpty());
        assertNotNull(userResponses.getData());
    }

    @Test
    public void getAllUserPaging() throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("Nga")
                .address("Hanoi")
                .phone("01111")
                .build();
        Page<UserResponse> pageU = new PageImpl<>(Collections.singletonList(userResponse));
        String uri = "/webapi/v1/user";
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(userService.findAllPaging(ArgumentMatchers.any()))
                .thenReturn(pageU);

        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Response<?> userResponses = objectMapper.readValue(content,
                Response.class);
        assertEquals(200, userResponses.getCode());
        assertNotNull(userResponses.getData());
        assertTrue((Integer) ((LinkedHashMap) userResponses.getData()).get("numberOfElements") > 0);
    }
}
