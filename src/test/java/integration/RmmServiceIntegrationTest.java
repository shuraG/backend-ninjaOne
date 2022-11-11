package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceEntity;
import com.ninjaone.backendinterviewproject.web.request.CreateRmmServicePriceReq;
import com.ninjaone.backendinterviewproject.web.response.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class RmmServiceIntegrationTest {

    public static final UUID RMM_SERVICE_ID = UUID.fromString("0000-00-00-00-000000");
    public static final String RMM_SERVICE_NAME = "backup";
    public static final BigDecimal RMM_SERVICE_PRICE = new BigDecimal(10.89);

    @Autowired
    private RmmServiceCrudRepository crudRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        var rmmServiceEntity = new RmmServiceEntity(RMM_SERVICE_ID, RMM_SERVICE_NAME, RMM_SERVICE_PRICE, null);
        crudRepository.save(rmmServiceEntity);
    }

    @AfterEach
    public void after() {
        this.crudRepository.deleteAll();
    }

    @Test
    public void givenRmmServiceRequestWhenCreateRmmServiceThenGetRmmServiceId() throws Exception {
        var requestDevice = new CreateRmmServicePriceReq("antivirus", TypeDevice.MAC, new BigDecimal("10.0"));
        var postRequest = post("/rmmservice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDevice));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rmmServiceId").value(crudRepository.findByName("antivirus").get().getId().toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenRmmServiceWithDuplicatedNameWhenCreateRmmServiceThenGetErrorDuplicated() throws Exception {
        var request = new CreateRmmServicePriceReq("Backup", TypeDevice.MAC, BigDecimal.ONE);
        var postRequest = post("/rmmservice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        var expectedResponse = new ErrorResponse("Duplicate, RmmService with attribute: name:backup is already exist!");

        mockMvc.perform(postRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
