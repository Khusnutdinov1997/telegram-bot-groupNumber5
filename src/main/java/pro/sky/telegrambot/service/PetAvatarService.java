package pro.sky.telegrambot.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.exception.PetAvatarNotFoundException;
import pro.sky.telegrambot.model.PetAvatar;
import pro.sky.telegrambot.repository.PetAvatarRepository;
import pro.sky.telegrambot.repository.PetRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional

public class PetAvatarService {

    @Value("${path.to.petavatars.folder}")

    private String petAatarsDir;

    private final PetRepository petRepository;
    private final PetAvatarRepository petAvatarRepository;

    private final Logger logger = LoggerFactory.getLogger(PetAvatarService.class);

    public PetAvatarService(PetRepository petRepository, PetAvatarRepository petAvatarRepository) throws IOException {
        this.petRepository = petRepository;
        this.petAvatarRepository = petAvatarRepository;
    }

    public void uploadPetAvatar(MultipartFile petAvatarFile) throws IOException {
        logger.info("Calling method uploadPetAvatar");
        byte[] data = petAvatarFile.getBytes();
        PetAvatar petAvatar = create(petAvatarFile.getSize(),  petAvatarFile.getContentType(), data);

        String extension = Optional.ofNullable(petAvatarFile.getOriginalFilename())
                .map(s -> s.substring(petAvatarFile.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        Path filePath = Paths.get(petAatarsDir).resolve(petAvatar.getId() + extension);
        Files.write(filePath, data);
        petAvatar.setFilePath(filePath.toString());
        petAvatarRepository.save(petAvatar);
    }

    private PetAvatar create (long size, String contentType, byte[] data) {
        PetAvatar petAvatar = new PetAvatar();
        petAvatar.setData(data);
        petAvatar.setFileSize(size);
        petAvatar.setMediaType(contentType);
        return petAvatarRepository.save(petAvatar);
    }

    public PetAvatar findPetAvatar(long id) {
        return petAvatarRepository.findById(id).orElse(new PetAvatar());
    }
}
