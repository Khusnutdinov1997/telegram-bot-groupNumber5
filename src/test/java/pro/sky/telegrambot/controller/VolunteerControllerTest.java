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
import pro.sky.telegrambot.model.Volunteer;

import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegrambot.constants.Constants.VOLUNTEER_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")


public class VolunteerControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findVolunteerTest() {
        // Создаем нового волонтера и проверяем что он создан OK
        Volunteer volunteer = new Volunteer(1L, "Ваня", "vanya", null);
        ResponseEntity<Volunteer> responseCreated = createVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        // Пытаемся получить волонетера по его id.
        Volunteer createdVolunteer = responseCreated.getBody();
        assert createdVolunteer != null;
        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getId(),
                Volunteer.class);

        // Проверяем что id волонтеров одинаковые
        Assertions.assertThat(response.getBody()).isEqualTo(createdVolunteer);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    void editVolunteerTest() {
        String oldName = "Vanya";
        String oldTelegramId = "vanya";
        String newName = "Vasya";
        String newTelegramId = "vasya";

        // Создаем нового волонтера и проверяем что он создан OK
        Volunteer volunteer = new Volunteer(1L, oldName, oldTelegramId, null);
        ResponseEntity<Volunteer> responseCreated = createVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        // Меняем соданного волонтера
        Volunteer createdVolunteer = responseCreated.getBody();
        assert createdVolunteer != null;
        createdVolunteer.setName(newName);
        createdVolunteer.setTelegramId(newTelegramId);

        // Сохраняем в базе волонтера
        restTemplate.put(
                LOCALHOST_URL + port + VOLUNTEER_URL,
                createdVolunteer);

        // получаем сохраненного волонтера по его id.
        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getId(),
                Volunteer.class);


        // Проверяем что у нового волонтера новые Name, newTelegramId.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newName);

    }

    private ResponseEntity<Volunteer> createVolunteerResponse(Volunteer volunteer) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL,
                volunteer,
                Volunteer.class);
    }

    private void assertCreatedVolunteer(Volunteer volunteer, ResponseEntity<Volunteer> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(volunteer.getId());
    }
}
