package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceEntity;
import com.ninjaone.backendinterviewproject.web.request.CreateDeviceReq;
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
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class DeviceIntegrationTest {
    public static final UUID DEVICE_ID = UUID.fromString("0000-00-00-00-000000");
    public static final String DEVICE_SYSTEM_NAME = "LAPTOP_12345";
    public static final TypeDevice TYPE_DEVICE = TypeDevice.WINDOWS;

    @Autowired
    DeviceCrudRepository crudRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        var deviceEntity = new DeviceEntity(DEVICE_ID, DEVICE_SYSTEM_NAME, TYPE_DEVICE, new ArrayList<>());
        this.crudRepository.save(deviceEntity);
    }

    @AfterEach
    public void after() {
        this.crudRepository.deleteAll();
    }

    @Test
    public void checkContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertNotNull(webApplicationContext.getBean("deviceController"));
    }

    @Test
    public void givenDeviceWhenCreateDeviceThenGetDeviceId() throws Exception {
        var requestDevice = new CreateDeviceReq("Petronia CBA", "MAC");
        var postRequest = post("/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDevice));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deviceId").value(crudRepository.findBySystemName("Petronia CBA").get().getId().toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenDeviceDuplicatedSystemNameWhenCreateDeviceThenGetErrorDuplicated() throws Exception {
        var requestDevice = new CreateDeviceReq(DEVICE_SYSTEM_NAME, TYPE_DEVICE.toString());
        var postRequest = post("/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDevice));

        var expectedResponse = new ErrorResponse("Duplicate, Device with attribute: SystemName:LAPTOP_12345 is already exist!");

        mockMvc.perform(postRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
