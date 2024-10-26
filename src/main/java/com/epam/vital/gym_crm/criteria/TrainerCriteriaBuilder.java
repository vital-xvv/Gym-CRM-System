package com.epam.vital.gym_crm.criteria;

import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TrainerCriteriaBuilder {

    private EntityManager em;

    @Autowired
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Training> findAllTrainerTrainingsByCriteria(
            String trainerUsername,
            LocalDateTime from,
            LocalDateTime to,
            String traineeName,
            TrainingType trainingType
    ) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);

        Root<Training> root = criteriaQuery.from(Training.class);
        Join<Training, Trainer> trainerJoin = root.join("trainer", JoinType.INNER);
        Join<Training, Trainee> traineeJoin = root.join("trainee", JoinType.INNER);
        Join<Join<Training, Trainee>, User> userJoinTrainee =  traineeJoin.join("user", JoinType.INNER);
        Join<Join<Trainer, Trainee>, User> userJoinTrainer =  trainerJoin.join("user", JoinType.INNER);

        Predicate dateTimeBetween = criteriaBuilder.between(root.get("dateTime"), from , to);
        Predicate hasTraineeWithName = criteriaBuilder.equal(userJoinTrainee.get("firstName"), traineeName);
        Predicate hasTrainerWithUsername = criteriaBuilder.equal(userJoinTrainer.get("username"), trainerUsername);

        Predicate andPredicate = criteriaBuilder.and(dateTimeBetween, hasTraineeWithName, hasTrainerWithUsername);

        criteriaQuery.where(andPredicate);

        TypedQuery<Training> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Trainer> findTrainersWithNoMutualTrainingsWithTrainee(String traineeUsername) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);

        Root<Training> root = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = root.join("trainee", JoinType.INNER);
        Join<Join<Training, Trainee>, User> userJoinTrainee =  traineeJoin.join("user", JoinType.INNER);

        Predicate exceptTraineeWithUsername = criteriaBuilder.notEqual(userJoinTrainee.get("username"), traineeUsername);

        criteriaQuery.where(exceptTraineeWithUsername);

        TypedQuery<Trainer> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
