package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.GymCrmApplicationTests;
import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.User;
import static org.junit.jupiter.api.Assertions.*;

import com.epam.vital.gym_crm.service.TrainerService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TrainerServiceTests extends GymCrmApplicationTests {
    private static final TrainerService service = applicationContext.getBean(TrainerService.class);
    private static final List<User> userList = (List<User>) applicationContext.getBean("userList", List.class);
    private static final List<Trainer> trainerList = service.getListOfTrainers();

    static {
        System.out.println(trainerList);
        System.out.println(userList);
    }

    @Test
    public void testGetTrainerById() {
        //when
        Trainer trainer1 = service.findTrainerById(1L);
        Trainer trainer2 = service.findTrainerById(2L);
        Trainer trainer5 = service.findTrainerById(5L);

        //then
        assertNotNull(trainer1);
        assertNotNull(trainer2);
        assertNotNull(trainer5);

        assertEquals(trainer1.getUser().getId(), 1L);
        assertEquals(trainer2.getUser().getId(), 6L);
        assertEquals(trainer5.getUser().getId(), 21L);

        assertTrue(getUserById(1L).isPresent());
        assertTrue(getUserById(6L).isPresent());
        assertTrue(getUserById(21L).isPresent());

    }

//    @Test
//    public void testCreateTrainer() {
//        //before
//        service.getListOfTrainers().remove(service.getListOfTrainers().size() - 1);
//        Trainer trainer = new Trainer(5L, List.of(Specialization.FITNESS_TRAINER), 21L);
//        Trainer trainer2 = new Trainer(5L, List.of(Specialization.FITNESS_TRAINER), 21L);
//
//        //when
//        boolean created1 = service.createTrainer(trainer);
//        boolean created2 = service.createTrainer(trainer2);
//
//        //then
//        assertTrue(created1);
//        assertFalse(created2);
//
//        assertEquals(service.getListOfTrainers().size(), 5);
//        assertEquals(service.getListOfTrainers().get(service.getListOfTrainers().size() -1).getId(), 5);
//
//    }

//    @Test
//    public void testUpdateTrainer() {
//        //before
//        Trainer trainer = new Trainer(5L, List.of(Specialization.SPORTS_TRAINER), 7L);
//        Trainer trainer2 = new Trainer(25L, List.of(Specialization.SPORTS_TRAINER), 7L);
//
//        //when
//        boolean updated = service.updateTrainer(trainer);
//        boolean updated2 = service.updateTrainer(trainer2);
//
//        //then
//        assertTrue(updated);
//        assertFalse(updated2);
//
//        assertEquals(service.findTrainerById(5L).getUserId(), 7L);
//        assertEquals(service.findTrainerById(5L).getTrainerSpecializations().size(), 1);
//        assertEquals(service.findTrainerById(5L).getTrainerSpecializations().get(0), Specialization.SPORTS_TRAINER);
//        assertEquals(service.getListOfTrainers().size(), 5);
//    }

    private static Optional<User> getUserById(Long id) {
        return userList.stream().filter(u -> u.getId().longValue() == id.longValue()).findFirst();
    }
}
