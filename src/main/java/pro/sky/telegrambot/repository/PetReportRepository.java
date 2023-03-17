package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.PetReport;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {

}
