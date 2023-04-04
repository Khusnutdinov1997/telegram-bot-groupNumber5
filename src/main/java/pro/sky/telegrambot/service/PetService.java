package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.PetAvatarNotFoundException;
import pro.sky.telegrambot.exception.PetNotFoundException;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetAvatar;
import pro.sky.telegrambot.repository.PetAvatarRepository;
import pro.sky.telegrambot.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@Service

public class PetService {


    private final PetRepository petRepository;
    private final PetAvatarRepository petAvatarRepository;

    public PetService(PetRepository petRepository, PetAvatarRepository petAvatarRepository) {
        this.petRepository = petRepository;
        this.petAvatarRepository = petAvatarRepository;
    }

    public Pet findPet(Long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            throw new PetNotFoundException(id);
        }
        return pet;
    }

    public Pet createPet(Pet pet) {
        pet.setId(null);
        return petRepository.save(pet);
    }

    public Pet editPet(Pet pet) {
        if (petRepository.findById(pet.getId()).orElse(null) == null) {
            return null;
        }
        return petRepository.save(pet);
    }

    public List<Pet> getAllVacantPets() {
        List<Pet> petsList = petRepository.findVacantPet();
        if (petsList.isEmpty()) {
            return null;
        }
        return petsList;
    }

    public Pet patchPetAvatar(long petId, long petAvatarId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        Optional <PetAvatar> optionalPetAvatar = petAvatarRepository.findById(petAvatarId);

        if(optionalPet.isEmpty()) {
            throw new PetNotFoundException(petId);
        }
        if(optionalPetAvatar.isEmpty()) {
            throw new PetAvatarNotFoundException(petAvatarId);
        }
        Pet pet = optionalPet.get();
        //PetAvatar petAvatar = optionalPetAvatar.get();

        pet.setAvatarId(petAvatarId);
        return petRepository.save(pet);
    }

}
