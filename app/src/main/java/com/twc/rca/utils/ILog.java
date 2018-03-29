package com.twc.rca.utils;

import android.util.Log;

import com.twc.rca.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Sushil
 * <p>
 * Custom Log file to print logs only during DEBUG, and other additional features
 */
public class ILog {

    private static final Boolean printLogs = BuildConfig.DEBUG;

    public static void d(String TAG, String string) {
        if (!printLogs)
            return;
        Log.d("BB " + TAG, string);
    }

    public static void e(String TAG, String string) {
        if (!printLogs)
            return;
        Log.e("BB " + TAG, string);
    }

    public static void appendLog(String text) {
        if (!printLogs)
            return;
        d("BB", text);
        File logFile = new File("sdcard/BBlogfile.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                    true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
