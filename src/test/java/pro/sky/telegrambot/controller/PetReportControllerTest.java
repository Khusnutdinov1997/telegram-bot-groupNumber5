package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.telegrambot.model.PetReport;

import static pro.sky.telegrambot.constants.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

public class PetReportControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void createPetReportTest() {
        PetReport petReport = new PetReport(1L,null, null, null, "1", "1","1");
        ResponseEntity<PetReport> responseCreated = createPetReport(petReport);
        assertCreatedPetReport(petReport, responseCreated);
    }

    @Test
    void updatePetReportTest() {
        String oldDiet = "1";
        String oldLifeStatus = "1";
        String oldBehavior = "1";
        String newDiet = "2";
        String newLifeStatus = "2";
        String newBehavior = "2";

        // создаем новый PetReport и проверяем что он ОК
        PetReport petReport = new PetReport(1L,null, null, null, oldDiet, oldLifeStatus,oldBehavior);
        ResponseEntity<PetReport> responseCreated = createPetReport(petReport);
        assertCreatedPetReport(petReport, responseCreated);

        // Меняем PetReport на новые значения
        PetReport createdPetReport = responseCreated.getBody();
        assert createdPetReport != null;
        createdPetReport.setDiet(newDiet);
        createdPetReport.setLifeStatus(newLifeStatus);
        createdPetReport.setBehavior(newBehavior);

        restTemplate.put(
                LOCALHOST_URL + port + PET_REPORT_URL,
                createdPetReport);

        // получаем сохраненный отчет по его id.
        ResponseEntity<PetReport> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_REPORT_URL + '/' +  createdPetReport.getId(),
                PetReport.class);


        // Проверяем что обновленный PetReport поменял 3 поля на новые
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getDiet()).isEqualTo(newDiet);
        Assertions.assertThat(response.getBody().getLifeStatus()).isEqualTo(newLifeStatus);
        Assertions.assertThat(response.getBody().getBehavior()).isEqualTo(newBehavior);
    }

    @Test
    void findPetReportTest() {
        // создаем новый PetReport и проверяем что он ОК
        PetReport petReport = new PetReport(1L,null, null, null, "1", "1","1");
        ResponseEntity<PetReport> responseCreated = createPetReport(petReport);
        assertCreatedPetReport(petReport, responseCreated);

        // Пытаемся получить созданный Pet Report по его id.
        PetReport createdPetReport = responseCreated.getBody();
        assert createdPetReport != null;
        ResponseEntity<PetReport> responsePetReport = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_REPORT_URL + '/' +  createdPetReport.getId(),
                PetReport.class);

        // проверяем что PetReport id совпадают
        Assertions.assertThat(responsePetReport.getBody().getId()).isNotNull();
    }


    private ResponseEntity<PetReport> createPetReport(PetReport petReport) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + PET_REPORT_URL,
                petReport,
                PetReport.class);
    }
    private void assertCreatedPetReport(PetReport petReport, ResponseEntity<PetReport> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(petReport.getId());
    }

}
