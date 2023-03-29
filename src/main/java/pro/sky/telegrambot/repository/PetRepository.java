package pro.sky.telegrambot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Pet;

import java.util.Collection;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(value = "select * from pet" +
            "where adopter_id = null" +
            "order by id"
            ,nativeQuery = true)
    Collection<Pet> findVacantPet();

}

