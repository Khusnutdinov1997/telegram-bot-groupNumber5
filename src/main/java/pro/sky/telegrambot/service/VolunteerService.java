package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service

public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer findVolunteer(long id) {
        logger.debug("Calling method find Volunteer (id = {})", id);
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer == null) {
            throw new VolunteerNotFoundException(id);
        }
        return volunteer;
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer editVolunteer(long id) {
        logger.debug("Calling method edit Volunteer (id = {})", id);
        Volunteer volunteer = findVolunteer(id);
        return volunteerRepository.save(volunteer);
    }

    public Volunteer deleteVolunteer(long id) {
        logger.debug("Calling method delete Volunteer (Id = {})", id);
        Volunteer volunteer = findVolunteer(id);
        volunteerRepository.deleteById(id);
        return volunteer;
    }

}
