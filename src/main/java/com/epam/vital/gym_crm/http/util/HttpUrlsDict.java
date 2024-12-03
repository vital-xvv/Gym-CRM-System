package com.epam.vital.gym_crm.http.util;

public class HttpUrlsDict {
    public static final String TRAINEE_URL = "/trainee";
    public static final String TRAINER_URL = "/trainer";
    public static final String TRAINING_URL = "/training";
    public static final String USER_URL = "/user";

    // VERSIONS
    public static final String VERSION_1 = "/v1";
    public static final String CURRENT_VERSION = VERSION_1;

    //BASIC URLS
    public static final String CREATE_URL = "/create";
    public static final String UPDATE_URL = "/update";
    public static final String DELETE_URL = "/delete";
    public static final String LOGIN_URL = "/delete";
    public static final String LOGOUT_URL = "/delete";

    //USER ENDPOINTS
    public static final String LOGOUT_USER_URL = USER_URL + CURRENT_VERSION + LOGOUT_URL;
    public static final String LOGIN_USER_URL = USER_URL + CURRENT_VERSION + LOGIN_URL;

    //TRAINER ENDPOINTS
    public static final String CREATE_TRAINER_URL = TRAINER_URL + CURRENT_VERSION + CREATE_URL;
    public static final String CREATE_TRAINEE_URL = TRAINEE_URL + CURRENT_VERSION + CREATE_URL;
}
