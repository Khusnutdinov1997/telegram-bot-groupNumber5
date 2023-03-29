package pro.sky.telegrambot.controller;


import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.model.PetAvatar;
import pro.sky.telegrambot.service.PetAvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("group5_petbot/params")

public class PetAvatarController {

    private final PetAvatarService petAvatarService;

    public PetAvatarController(PetAvatarService petAvatarService) {
        this.petAvatarService = petAvatarService;
    }

    @PostMapping(value = "/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPetAvatar(@RequestParam MultipartFile petAvatarFile) throws IOException {
        petAvatarService.uploadPetAvatar(petAvatarFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadPetAvatarFromDb(@PathVariable long id, HttpServletResponse response) throws IOException {
        PetAvatar petAvatar = petAvatarService.findPetAvatar(id);
        Path path = Path.of(petAvatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(petAvatar.getMediaType());
            response.setContentLength((int) petAvatar.getFileSize());
            is.transferTo(os);
        }

    }



}
