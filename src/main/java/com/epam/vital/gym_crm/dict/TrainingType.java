package com.epam.vital.gym_crm.dict;

public enum TrainingType {
    STRENGTH_TRAINING("Strength Training"),
    CARDIO("Cardio"),
    HIGH_INTENSITY_INTERVAL_TRAINING("High-Intensity Interval Training"),
    FLEXIBILITY("Flexibility Training"),
    BALANCE_TRAINING("Balance Training"),
    ENDURANCE("Endurance Training"),
    CIRCUIT_TRAINING("Circuit Training"),
    BODYBUILDING("Bodybuilding"),
    CROSSFIT("CrossFit"),
    YOGA("Yoga"),
    PILATES("Pilates"),
    FUNCTIONAL_TRAINING("Functional Training"),
    MARTIAL_ARTS("Martial Arts"),
    BOOTCAMP("Bootcamp"),
    AEROBICS("Aerobics");

    private final String displayName;

    TrainingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
