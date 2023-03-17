package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.BranchParams;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {

}
