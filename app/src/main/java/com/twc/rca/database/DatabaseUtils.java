package com.twc.rca.database;


import com.twc.rca.BuildConfig;

/**
 * Created by mihir on 14-08-2015.
 */
public class DatabaseUtils {

    private static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;

    public static String DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";
}
