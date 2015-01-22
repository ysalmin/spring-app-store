package com.shoppingcart.controllers;

import com.shoppingcart.service.ShopService;
//import org.junit.Before;
//import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

/**
 * Created by ysalmin on 03.12.2014.
 */
public class DashboardControllerTest {
    @Mock
    private ShopService shopService;

    @InjectMocks
    private DashboardController dashboardController;

    private MockMvc mockMvc;

   /* @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();

    }

    @Test
    public void testSaveComment_RequestCommentNotFound() throws Exception {

    }*/

}
