package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.BranchNotFoundException;
import pro.sky.telegrambot.model.BranchParams;
import pro.sky.telegrambot.repository.BranchParamsRepository;

@Service

public class BranchService {

    private final BranchParamsRepository branchParamsRepository;

    public BranchService(BranchParamsRepository branchParamsRepository) {
        this.branchParamsRepository = branchParamsRepository;
    }

    public BranchParams createBranch(BranchParams branchParams) {
        branchParams.setId(null);
        return branchParamsRepository.save(branchParams);
    }

    public BranchParams editBranch(BranchParams branchParams) {
        if (branchParamsRepository.findById(branchParams.getId()).orElse(null) == null) {
            return null;
        }
        return branchParamsRepository.save(branchParams);
    }

    public BranchParams findBranchById(long id) {
        BranchParams branchParams = branchParamsRepository.findById(id).orElse(null);
        if (branchParams == null) {
            throw new BranchNotFoundException(id);
        }
        return branchParams;
    }
}
