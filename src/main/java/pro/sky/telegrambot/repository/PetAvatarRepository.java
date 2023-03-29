package pro.sky.telegrambot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.PetAvatar;

public interface PetAvatarRepository extends JpaRepository<PetAvatar,Long> {
}
