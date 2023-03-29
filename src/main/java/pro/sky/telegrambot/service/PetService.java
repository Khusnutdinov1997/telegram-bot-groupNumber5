package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.PetAvatarNotFoundException;
import pro.sky.telegrambot.exception.PetNotFoundException;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetAvatar;
import pro.sky.telegrambot.repository.PetAvatarRepository;
import pro.sky.telegrambot.repository.PetRepository;

import java.util.Collection;
import java.util.Optional;

@Service

public class PetService {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final PetRepository petRepository;
    private final PetAvatarRepository petAvatarRepository;

    public PetService(PetRepository petRepository, PetAvatarRepository petAvatarRepository) {
        this.petRepository = petRepository;
        this.petAvatarRepository = petAvatarRepository;
    }

    public Pet findPet(long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            throw new PetNotFoundException(id);
        }
        return pet;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet editPet(Pet pet) {
        if (petRepository.findById(pet.getId()).orElse(null) == null) {
            return null;
        }
        return petRepository.save(pet);
    }

    public ResponseEntity<Collection<Pet>> getAllVacantPets() {
        Collection<Pet> petsList = petRepository.findVacantPet();
        if (petsList.isEmpty()) {
            logger.error("No vacant pets exist in DB!");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petsList);
    }

    public Pet patchPetAvatar(long petId, long petAvatarId) {
        logger.debug("Calling method patchPetAvatar (petId = {}, petAvatarId = {})", petId, petAvatarId);
        Optional<Pet> optionalPet = petRepository.findById(petId);
        Optional <PetAvatar> optionalPetAvatar = petAvatarRepository.findById(petAvatarId);

        if(optionalPet.isEmpty()) {
            throw new PetNotFoundException(petId);
        }
        if(optionalPetAvatar.isEmpty()) {
            throw new PetAvatarNotFoundException(petAvatarId);
        }
        Pet pet = optionalPet.get();
        PetAvatar petAvatar = optionalPetAvatar.get();

        pet.setPetAvatar(petAvatar);
        return petRepository.save(pet);
    }

}
