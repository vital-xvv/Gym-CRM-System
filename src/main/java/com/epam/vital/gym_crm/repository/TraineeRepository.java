package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TraineeRepository {
    private List<Trainee> traineeList;

    @Autowired
    public void setTraineeList(List<Trainee> traineeList) {
        this.traineeList = traineeList;
    }

    public List<Trainee> getAllTrainees() {
        return traineeList;
    }

    public Optional<Trainee> getById(Long id) {
        return traineeList.stream().filter(t -> t.getId().longValue() == id.longValue()).findFirst();
    }

    public void deleteById(Long id) {
        EntryStream.of(traineeList)
                .filter(t -> t.getValue().getId().longValue() == id.longValue())
                .forKeyValue((k, v) -> traineeList.remove(k.intValue()));
    }

    public boolean create(Trainee trainee) {
        if (trainee.getId() != null && traineeList.stream().anyMatch(t -> t.getId().longValue() == trainee.getId().longValue()))
            return false;
        trainee.setId(traineeList.get(traineeList.size() - 1).getId() + 1);
        return traineeList.add(trainee);
    }

    public boolean update(Trainee trainee){
        Optional<Trainee> traineeOptional = getById(trainee.getId());
        if (traineeOptional.isEmpty()) {
            log.error("An entity with id %d already exists.".formatted(trainee.getId()));
            return false;
        }

        Trainee traineeToUpdate = traineeOptional.get();
        traineeToUpdate.setBirthDate(trainee.getBirthDate());
        traineeToUpdate.setAddress(trainee.getAddress());
        traineeToUpdate.setUserId(trainee.getUserId());
        return true;
    }
}
