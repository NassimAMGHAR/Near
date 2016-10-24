package com.near.friendly.core;

/**
 * @author  by ayassinov on 22/10/2016.
 */
public class Constants {

    //Parameter names of the deployment enviroment
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    //Authorities
    public static final String ADMIN = "ADMIN";
    public static final String SYSTEM_ACCOUNT = "SYSTEM";
    public static final String ANONYMOUS = "ANONYMOUS";

    //private key for the cookies
    public static final String SECRET_KEY = "9b85ab1eeffa1d104cc21ba6c26edeb9ecd50335";

    public static final int DEFAULT_USER_SESSION_ID_LENGTH = 16;
    public static final int DEFAULT_USER_SESSION_TOKEN_LENGTH = 16;

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";


}
