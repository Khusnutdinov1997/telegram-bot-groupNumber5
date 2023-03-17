package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.BranchParams;
import pro.sky.telegrambot.service.BranchService;

@RestController
@RequestMapping("group5_petbot/params")


public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @Operation(
            summary = "Добавление нового бранча",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавление нового бранча",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BranchParams.class)
                            )
                    )
            },
            tags = "Обработка бранчей",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BranchParams.class)
                    )
            )
    )
    @PostMapping
    public BranchParams createBranch(@RequestBody BranchParams branchParams) {
        return branchService.createBranch(branchParams);
    }

    @Operation(
            summary = "Редактирование бранча",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный бранч",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BranchParams.class)
                            )
                    )
            },
            tags = "Обработка бранчей",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BranchParams.class)
                    )
            )
    )
    @PutMapping
    public BranchParams editBranch(@RequestBody BranchParams branchParams) {
        return branchService.editBranch(branchParams);
    }

    @Operation(
            summary = "Поиск бранча по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден бранч",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BranchParams.class)
                            )
                    )
            },

            tags = "Обработка бранчей",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового бранча",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BranchParams.class)
                    )
            )
    )
    @GetMapping("{id}")
    public BranchParams getBranchById(@Parameter(description = "id бранча", example = "1") @PathVariable Integer id) {
        return branchService.findBranchById(id);
    }

}
