package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.BranchParams;

@Repository
public interface ServiceTableRepository extends JpaRepository <BranchParams,String> {

    @Query(value = "select meet_pet from service_level2",nativeQuery = true)
    String getMeetPetRules();

    @Query(value = "select adoption_docs from service_level2",nativeQuery = true)
    String getAdoptionDocs();

    @Query(value = "select transport_pet from service_level2",nativeQuery = true)
    String getTransportPet();

    @Query(value = "select house_puppy from service_level2",nativeQuery = true)
    String getHouseForPuppy();

    @Query(value = "select house_big_dog from service_level2",nativeQuery = true)
    String getHouseForBigDog();

    @Query(value = "select house_handicapped from service_level2",nativeQuery = true)
    String getHouseForHandicappedDog();

    @Query(value = "select advice_specialist from service_level2",nativeQuery = true)
    String getSpecialistAdvice();

    @Query(value = "select contacts_specialist from service_level2",nativeQuery = true)
    String getSpecialistContact();

    @Query(value = "select refusal_reasons from service_level2",nativeQuery = true)
    String getRefusalReasons();

}
