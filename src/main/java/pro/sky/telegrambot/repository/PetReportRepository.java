package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.PetReport;

@Repository
public interface PetReportRepository extends JpaRepository<PetReport, Long> {

}
