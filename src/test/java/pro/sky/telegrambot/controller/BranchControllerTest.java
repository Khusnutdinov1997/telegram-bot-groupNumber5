package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RequestBody;
import pro.sky.telegrambot.model.BranchParams;

import static pro.sky.telegrambot.constants.Constants.BRANCHPARAMS_URL;
import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

public class BranchControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createBranchTest() {
        BranchParams branchParams = new BranchParams(1L, "Branch 1");
        BranchParams response = createBranch(branchParams);
        assertCreatedBranchParams(branchParams, response);
    }

    @Test
    public void editBranchTest() {
        String oldName = "Branch 1";
        String newName = "Updated Branch 1";

        // Создаем новый бранч и проверяем что он создан OK
        BranchParams branchParams = new BranchParams(1L, oldName);
        BranchParams responseCreated = createBranch(branchParams);
        assertCreatedBranchParams(branchParams, responseCreated);

        // Меняем имя бранча
        responseCreated.setName(newName);

        restTemplate.put(
                LOCALHOST_URL + port + BRANCHPARAMS_URL,
                responseCreated);

        // получаем обновленную Branch по ее id
        BranchParams response = restTemplate.getForObject(
                LOCALHOST_URL + port + BRANCHPARAMS_URL + '/' + responseCreated.getId(),
                BranchParams.class);

        // проверяем что имя ну обновленного Branch новое
        Assertions.assertThat(response.getName()).isEqualTo(newName);
    }

    @Test
    public void findBranchByIdTest() {
        // Создаем новый бранч и проверяем что он создан OK
        BranchParams branchParams = new BranchParams(1L, "Branch 1");
        BranchParams response = createBranch(branchParams);
        assertCreatedBranchParams(branchParams, response);

        // получаем обновленную Branch по ее id.
        BranchParams responseBranch = restTemplate.getForObject(
                LOCALHOST_URL + port + BRANCHPARAMS_URL + '/' + response.getId(),
                BranchParams.class);

        // Check that the created and selected by id branches are the same
        Assertions.assertThat(responseBranch.getId()).isNotNull();
        }

    private BranchParams createBranch(@RequestBody BranchParams branchParams) {
        return restTemplate.postForObject(
                LOCALHOST_URL + port + BRANCHPARAMS_URL,
                branchParams,
                BranchParams.class);
    }

    private void assertCreatedBranchParams(BranchParams branchParams, BranchParams response) {
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(branchParams.getId());
    }
}

