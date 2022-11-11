package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.domain.device.TypeDevice;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.device.DeviceEntity;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceCrudRepository;
import com.ninjaone.backendinterviewproject.infraestructure.jpa.rmmservice.RmmServiceEntity;
import com.ninjaone.backendinterviewproject.web.response.TotalCostResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@AutoConfigureMockMvc
@WebAppConfiguration
public class SubscriptionIntegrationTest {
    public static final UUID ANTIVIRUS_SERVICE_ID = UUID.fromString("0000-00-00-00-000000");
    public static final UUID DEVICE_ID = UUID.fromString("0000-00-00-00-000001");
    public static final UUID DEVICE_ID_WITH_SUBSCRIPTION = UUID.fromString("0000-00-00-00-000002");

    @Autowired
    private DeviceCrudRepository deviceCrudRepository;

    @Autowired
    private RmmServiceCrudRepository rmmServiceCrudRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {

        var antivirusService = getServiceAntivirus();
        rmmServiceCrudRepository.save(antivirusService);

        var deviceEntity = new DeviceEntity(DEVICE_ID, "LAPTOP_123", TypeDevice.MAC, List.of());

        var deviceEntityWithSubscription = new DeviceEntity(
                DEVICE_ID_WITH_SUBSCRIPTION,
                "ANDR_123",
                TypeDevice.WINDOWS,
                List.of(antivirusService.getRmmService()));

        deviceCrudRepository.saveAll(List.of(deviceEntity, deviceEntityWithSubscription));
    }

    @AfterEach
    public void after() {
        this.deviceCrudRepository.deleteAll();
        this.rmmServiceCrudRepository.deleteAll();
    }

    @Test
    public void giveRmmServiceWithoutSubscriptionWhenGetCostThenGettingZeroCost() throws Exception {
        var request = List.of(DEVICE_ID);
        var postRequest = get("/device/cost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        var expectedResponse = objectMapper.writeValueAsString(new TotalCostResponse(BigDecimal.ZERO));

        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void giveRmmServiceWithSubscriptionWhenGetCostThenRightCost() throws Exception {
        var request = List.of(DEVICE_ID_WITH_SUBSCRIPTION);
        var postRequest = get("/device/cost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        var expectedResponse = objectMapper.writeValueAsString(new TotalCostResponse(new BigDecimal(9.0)));

        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    private RmmServiceEntity getServiceAntivirus() {
        var pricesAntivirus = new HashMap<TypeDevice, BigDecimal>();
        pricesAntivirus.put(TypeDevice.WINDOWS, new BigDecimal(5.0));
        pricesAntivirus.put(TypeDevice.MAC, new BigDecimal(7.0));
        return new RmmServiceEntity(ANTIVIRUS_SERVICE_ID, "antivirus", null, pricesAntivirus);
    }

}
