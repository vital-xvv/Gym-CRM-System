package com.epam.vital.gym_crm.domain.dict;

import lombok.Getter;

@Getter
public enum Specialization {
    HEALTH_COACH("Health coach"), FITNESS_TRAINER("Fitness trainer"),
    PHYSIQUE_TRAINER("Physique trainer"), LIFESTYLE_PERSONAL_TRAINER("Lifestyle trainer"),
    BODYBUILDING_COACH("Bodybuilding coach"), SPORTS_TRAINER("Sports trainer"),
    EXERCISE_SPECIALIST("Exercise specialist"), CROSSFIT_COACH("Crossfit coach");

    private final String name;

    Specialization(String name){
        this.name = name;
    }
}
