package pro.sky.telegrambot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.service.PetReportService;

@RestController
@RequestMapping("/group5_petbot/petreport")

public class PetReportController {

    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    @PostMapping()
    public ResponseEntity<PetReport> createPetReport(@RequestBody PetReport petReport) {
        PetReport petReportToCreate = petReportService.createPetReport(petReport);
        if (petReportToCreate == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(petReportToCreate);
     }

    @PutMapping("{petReportId}")
    public ResponseEntity <PetReport> updatePetReport(@PathVariable long petReportId,
                                               @RequestBody PetReport petReport) {
        PetReport petReportToEdit = petReportService.updatePetReport(petReportId, petReport);
        if (petReportToEdit == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(petReportToEdit);
    }

    @GetMapping("{petReportId}")
    public ResponseEntity<PetReport> findPetReport(@PathVariable long petReportId) {
        PetReport findPetReport = petReportService.findPetReport(petReportId);
        if (findPetReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findPetReport);
    }
    @DeleteMapping("{petReportId}")
    public void deletePetReport(@PathVariable long petReportId) {
        petReportService.deletePetReport(petReportId);
    }
}
