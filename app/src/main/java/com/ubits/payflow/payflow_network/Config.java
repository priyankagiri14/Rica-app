package com.ubits.payflow.payflow_network;

/**
 * Created by sauda on 2017/08/08.
 */

public class Config {
    //JSON URL
    public static final String DATA_URL = "http://simplifiedcoding.16mb.com/Spinner/json.php";
    //BASE URL
    public static final String BASE_URL = "http://ontrackerp.co.za/";

    public static final String RICA_URL = BASE_URL + "app/rica/register.php";

    public static final String AGENT_REGISTER_URL = BASE_URL + "app/rica/agentregister.php";
    //this will only update in database
    public static final String RICA_OFFLINE_URL = BASE_URL + "app/rica/rica_sim.php";

    public static final String API_BARCODE = BASE_URL + "app/barcode";

    public static final String API_ALLOCATE_BATCH = BASE_URL + "app/allocatebatch/index.php";
}
