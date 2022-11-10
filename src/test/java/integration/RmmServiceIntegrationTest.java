package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceCrudRepository;
import com.ninjaone.backendinterviewproject.web.request.CreateRmmServicePriceReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class RmmServiceIntegrationTest {
    @Autowired
    RmmServiceCrudRepository crudRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void after() {
        this.crudRepository.deleteAll();
    }

    @Test
    public void givenDeviceWhenCreateDeviceThenGetDeviceId() throws Exception {
        var requestDevice = new CreateRmmServicePriceReq("antivirus", TypeDevice.MAC, new BigDecimal("10.0"));
        var postRequest = post("/rmmservice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDevice));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rmmServiceId").value(crudRepository.findByName("antivirus").get().getId().toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
