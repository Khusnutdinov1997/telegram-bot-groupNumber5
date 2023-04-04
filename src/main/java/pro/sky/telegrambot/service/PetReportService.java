package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.repository.PetReportRepository;

@Service

public class PetReportService {

    private final PetReportRepository petReportRepository;

    public PetReportService(PetReportRepository petReportRepository) {
        this.petReportRepository = petReportRepository;
    }
    private final Logger logger = LoggerFactory.getLogger(PetReportService.class);


    public PetReport createPetReport(PetReport petReport) {
        return petReportRepository.save(petReport);
    }

    public PetReport findPetReport(long id) {
        logger.debug("Calling method find Pet Report (id = {})", id);
        return petReportRepository.findById(id).orElseThrow(() -> new NotFoundException("id not found"));
    }

    public PetReport updatePetReport(long id, PetReport petReport) {
        logger.debug("Calling method update Pet Report (id = {})", petReport.getId());
        PetReport updatedPetReport = findPetReport(id);
        updatedPetReport.setAdopterId(petReport.getAdopterId());
        updatedPetReport.setReportDate(petReport.getReportDate());
        updatedPetReport.setPhoto(petReport.getPhoto());
        updatedPetReport.setDiet(petReport.getDiet());
        updatedPetReport.setLifeStatus(petReport.getLifeStatus());
        updatedPetReport.setBehavior(petReport.getBehavior());
        return petReportRepository.save(updatedPetReport);
    }

    public PetReport deletePetReport(long id) {
        logger.debug("Calling method delete Pet Report (Id = {})", id);
        PetReport petReport = findPetReport(id);
        petReportRepository.deleteById(id);
        return petReport;
    }

}
