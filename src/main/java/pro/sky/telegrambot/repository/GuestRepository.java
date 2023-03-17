package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
