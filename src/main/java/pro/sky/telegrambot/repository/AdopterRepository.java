package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.BranchParams;
@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    public Adopter findByChatId(long chatId);

}
