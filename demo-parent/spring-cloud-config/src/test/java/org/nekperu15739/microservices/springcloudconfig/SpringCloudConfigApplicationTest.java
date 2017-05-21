package org.nekperu15739.microservices.springcloudconfig;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author nekperu15739
 * 
 * 
 * 
 * 
 *
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringCloudConfigApplicationTest {
    
    private static final String LABEL = "/label";
    @Autowired
    private MockMvc mvc;
    private static final String NAME = "licensingservice";
    private static final String PROFILES_DEV = "dev";
    private static final String ROUTE_ONE = "/%s/%s%s";
    private static final String ROUTE_TWO = "/%s-%s.yml";
    private static final String ROUTE_FOUR = "/%s-%s.properties";
    
    /**
     * /{application}/{profile}[/{label}]
     * 
     * @throws Exception
     */
    @Test
    public void routeOneWithOutLabelTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_ONE, NAME, PROFILES_DEV, StringUtils.EMPTY)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(containsString(NAME)));
    }
    
    /**
     * /{application}/{profile}[/{label}]
     * 
     * @throws Exception
     */
    @Test
    public void routeOneTest() throws Exception {
        String label = String.format(ROUTE_ONE, NAME, PROFILES_DEV, LABEL);
        this.mvc.perform(get(label).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString(NAME)));
    }
    
    /**
     * /{application}-{profile}.yml
     * 
     * @throws Exception
     */
    @Test
    public void routeTwoTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_TWO, NAME, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("author")));
    }
    
    /**
     * /{label}/{application}-{profile}.yml
     * 
     * @throws Exception
     */
    @Ignore
    @Test
    public void routeThreeTest() throws Exception {
        fail("not implement");
    }
    
    
    /**
     * /{label}/{application}-{profile}.properties
     * 
     * @throws Exception
     */
    @Test
    public void routeFourTest() throws Exception {
        this.mvc.perform(get(String.format(ROUTE_FOUR, NAME, PROFILES_DEV)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("author")));
    }

    /**
     *  /{application}-{profile}.properties
     */
    @Ignore
    @Test
    public void routeFiveTest() throws Exception {
        fail("not implement");
    }
}
