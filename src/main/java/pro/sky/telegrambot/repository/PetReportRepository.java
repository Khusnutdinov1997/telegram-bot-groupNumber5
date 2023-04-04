package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.PetReport;

import java.time.LocalDate;

@Repository
public interface PetReportRepository extends JpaRepository<PetReport, Long> {

    public PetReport findPetReportByAdopterIdAndReportDate(Adopter adopter,
                                                                LocalDate date);


}
