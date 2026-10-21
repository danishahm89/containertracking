package com.service.containertracking.controller;

import com.service.containertracking.entity.Container;
import com.service.containertracking.repository.ContainerRepo;
import com.service.containertracking.service.ContainerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContainerController.class)
public class ContainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContainerService containerService;

    @MockBean
    private ContainerRepo containerRepo;

    @Test
    public void testCreateContainer() throws Exception {
        String requestBody = "{ \"containerId\": \"1\", \"status\": \"NOT_STARTED\" }";
        when(containerService.createContainer(any())).thenReturn(new Container());
        mockMvc.perform(post("/containers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetContainerStatus_Found() throws Exception {
        Container container = new Container();
        container.setTrackingId(1);
        container.setStatus("LOADED");
        when(containerService.getContainerById(1))
                .thenReturn(Optional.of(container));
        mockMvc.perform(get("/containers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContainerStatus_NotFound() throws Exception {
        when(containerService.getContainerById(44444))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/containers/44444"))
                .andExpect(status().isNotFound());
    }
}
