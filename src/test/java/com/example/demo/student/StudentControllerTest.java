package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    private static final String URL = "api/v1/students/";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @ParameterizedTest
    @CsvSource({"annasmith,password,200,true", "fred,password123,401,false"})
    public void testOnlyCorrectlyAuthenticatedUsersCanObtainJwtToken(String username, String password, int statusCode, boolean checkToken) throws Exception {
        String jWtToken = performLogin(username, password, statusCode);
        if(checkToken) {
            assertThat(jWtToken.startsWith("Bearer"));
        } else {
            assertThat(jWtToken).isNullOrEmpty();
        }
    }

    @ParameterizedTest
    @CsvSource({"annasmith,password,200", "linda,password,403", "tom,password,403"})
    public void testOnlyAuthenticatedUserWithStudentRoleCanAccessAPI(String username, String password, int statusCode) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1")
                .header("Authorization", performLogin(username, password, 200)))
                .andExpect(status().is(statusCode));
    }

    private String performLogin(String username, String password, int expectedStatusCode) throws Exception {
        String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        MvcResult result = mvc.perform(post("/login").content(body)).andExpect(status().is(expectedStatusCode)).andReturn();

        return result.getResponse().getHeader("Authorization");
    }
}