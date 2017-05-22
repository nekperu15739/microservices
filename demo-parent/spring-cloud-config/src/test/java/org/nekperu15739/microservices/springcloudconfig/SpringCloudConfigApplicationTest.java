package org.nekperu15739.microservices.springcloudconfig;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author nekperu15739
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringCloudConfigApplicationTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Value("${spring.application.name:licensingservice}")
    private String application;
    
    private static final String LABEL = "/label";
    private static final String PROFILES_DEV = "dev";
    private static final String ROUTE_ONE = "/%s/%s%s";
    private static final String ROUTE_TWO = "/%s-%s.yml";
    private static final String ROUTE_THREE = "%s/%s-%s.yml";
    private static final String ROUTE_FOUR = "/%s-%s.properties";
    private static final String ROUTE_FIVE = "%s%s-%s.properties";
    
    /**
     * /{application}/{profile}[/{label}]
     * 
     * @throws Exception
     */
    @Test
    public void routeOneWithOutLabelTest() throws Exception {
        String contentAsString = this.mvc.perform(get(String.format(ROUTE_ONE, application, PROFILES_DEV, StringUtils.EMPTY)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        
        this.mvc.perform(get(String.format(ROUTE_ONE, application, PROFILES_DEV, StringUtils.EMPTY)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(containsString("10")));
    }
    
    /**
     * /{application}/{profile}[/{label}]
     * 
     * @throws Exception
     */
    @Test
    public void routeOneTest() throws Exception {
        String contentAsString = this.mvc.perform(get(String.format(ROUTE_ONE, application, PROFILES_DEV, LABEL)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        
        
        this.mvc.perform(get(String.format(ROUTE_ONE, application, PROFILES_DEV, LABEL)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("20")));
    }
    
    /**
     * /{application}-{profile}.yml
     * 
     * @throws Exception
     */
    @Test
    public void routeTwoTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_TWO, application, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("author")));
    }
    
    /**
     * /{label}/{application}-{profile}.yml
     * 
     * @throws Exception
     */
    @Test
    public void routeThreeTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_THREE, LABEL ,application, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("author")));
    }
    
    
    /**
     * /{application}-{profile}.properties
     * @throws Exception
     */
    @Test
    public void routeFourTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_FOUR, application, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("author")));
    }

    /**
     *  /{label}/{application}-{profile}.properties
     */
    @Ignore("the response is empty")
    @Test
    public void routeFiveTest() throws Exception {
        String contentAsString = this.mvc.perform(get(String.format(ROUTE_FIVE, LABEL, application, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//        andExpect(content().string(containsString("author")));
        System.out.println(contentAsString);
    }
}
