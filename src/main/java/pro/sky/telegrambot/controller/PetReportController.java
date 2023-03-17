package pro.sky.telegrambot.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.service.PetReportService;

@RestController

public class PetReportController {

    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    @PostMapping("/createPetReport")
    public PetReport createPetReport(@RequestBody PetReport petReport) {
        return petReportService.createPetReport(petReport);
    }

    @PutMapping("{petReportId}")
    public PetReport updatePetReport(@PathVariable long petReportId,
                                               @RequestBody PetReport petReport) {
        return petReportService.updatePetReport(petReportId, petReport);
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
