package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.service.PetService;

import java.util.List;

@RestController
@RequestMapping("group5_petbot/pet")


public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Поиск питомца по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами"

    )
    @GetMapping("{id}")
    public Pet getPet(@PathVariable @Parameter(description = "id питомца", example = "1") Long id) {
        return petService.findPet(id);
    }


    @Operation(
            summary = "Добавление питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен питомец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры питомца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )
    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @Operation(
            summary = "Редактирование питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновлен питомец",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры питомца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )
    @PutMapping
    public Pet editPet(@RequestBody Pet pet) {
        return petService.editPet(pet);
    }

        @Operation(
                summary = "Все доступные к усыновлению питомцы",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Выведен список питомецев",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Pet.class)
                                )
                        )
                },
                tags = "Работа с питомцами"
        )

        @GetMapping()
        public ResponseEntity<List<Pet>> printAllVacantPets() {
            return ResponseEntity.ok(petService.getAllVacantPets());
        }

    @Operation(
            summary = "Добавление питомцу ID его аватарки",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аватарка добавлена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами"

    )
    @PatchMapping()

    public Pet patchPetAvatar(@PathVariable @Parameter(description = "id питомца", example = "1") Long id,
                                      @RequestParam("petAvatarId") long petAvatarId) {
        return petService.patchPetAvatar(id,petAvatarId);
    }

}
