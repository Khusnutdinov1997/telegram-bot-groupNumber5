package pro.sky.telegrambot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.BranchParams;

@Repository
public interface BranchParamsRepository extends JpaRepository<BranchParams, Integer> {

}
