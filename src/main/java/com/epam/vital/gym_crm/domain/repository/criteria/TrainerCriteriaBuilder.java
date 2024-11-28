package com.epam.vital.gym_crm.domain.repository.criteria;

import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Training;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            String traineeName
    ) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);

        Root<Training> root = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = root.join("trainees", JoinType.INNER);
        Join<Training, Trainer> trainerJoin = root.join("trainer", JoinType.INNER);
        Join<Join<Training, Trainee>, User> userJoinTrainee =  traineeJoin.join("user", JoinType.INNER);
        Join<Join<Training, Trainer>, User> userJoinTrainer =  trainerJoin.join("user", JoinType.INNER);

        Predicate hasTrainerWithUsername = criteriaBuilder.equal(userJoinTrainer.get("username"), trainerUsername);
        Predicate fromDateTime = criteriaBuilder.greaterThanOrEqualTo(root.get("dateTime"), from);
        Predicate toDateTime = criteriaBuilder.lessThanOrEqualTo(root.get("dateTime"), to);
        Predicate hasTraineeWithName = criteriaBuilder.equal(userJoinTrainee.get("firstName"), traineeName);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(hasTrainerWithUsername);
        if (from != null) predicates.add(fromDateTime);
        if (to != null) predicates.add(toDateTime);
        if (traineeName != null) predicates.add(hasTraineeWithName);

        Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(andPredicate);

        TypedQuery<Training> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
