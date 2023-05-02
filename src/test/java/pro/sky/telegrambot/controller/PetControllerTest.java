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
import pro.sky.telegrambot.constants.PetColor;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.model.Pet;

import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegrambot.constants.Constants.PET_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

public class PetControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void findPetTest() {
        // Создаем нового питомца и проверяем что он создан OK
        Pet pet = new Pet(1L, "Шарик", PetType.DOG, PetColor.BLACK, 0,0);
        ResponseEntity<Pet> responseCreated = createPet(pet);
        assertCreatedPet(pet, responseCreated);

        // Пробуем найти пета по его Id
        Pet createdPet = responseCreated.getBody();
        assert createdPet != null;

        ResponseEntity<Pet> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_URL + '/' + createdPet.getId(),
                Pet.class);

        // Проверяем что созданный и найденые id петов одинаковые
        Assertions.assertThat(response.getBody()).isEqualTo(createdPet);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void editPetTest() {
        String oldNickName = "Шарик";
        PetColor oldColor = PetColor.BLACK;
        String newNickName = "Боба";
        PetColor newColor = PetColor.WHITE;

        // Создаем нового питомца и проверяем что он создан OK
        Pet pet = new Pet(1l, oldNickName, PetType.DOG, oldColor, 0,0);
        ResponseEntity<Pet> responseCreated = createPet(pet);
        assertCreatedPet(pet, responseCreated);

        // Меняем питомца на нового
        Pet createdPet = responseCreated.getBody();
        assert createdPet != null;
        createdPet.setNickName(newNickName);
        createdPet.setPetColor(newColor);

        // Сохраняем пета в базе
        restTemplate.put(
                LOCALHOST_URL + port + PET_URL,
                createdPet);

        // получаем сохраненного пета по его id.
        ResponseEntity<Pet> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_URL + '/' +  createdPet.getId(),
                Pet.class);

        // Проверяем что у нового пета новые newNickName, newColor.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getNickName()).isEqualTo(newNickName);
        Assertions.assertThat(response.getBody().getPetColor()).isEqualTo(newColor);
    }

    private ResponseEntity<Pet> createPet(Pet pet) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + PET_URL,
                pet,
                Pet.class);
    }

    private void assertCreatedPet(Pet pet, ResponseEntity<Pet> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(pet.getId());

    }

}
