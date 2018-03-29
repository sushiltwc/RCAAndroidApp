package com.twc.rca.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sushil on 15-03-2018
 */
public class DatabaseCreater {

    public static final String TAG = DatabaseCreater.class.getSimpleName();

    SQLiteOpenHelper mDatabaseHelper;

    String mDbName;

    Context mContext;

    int mDbVersion;

    public DatabaseCreater(Context context, SQLiteOpenHelper databaseHelper, String name, int version) {
        mContext = context;
        mDatabaseHelper = databaseHelper;
        mDbName = name;
        mDbVersion = version;
    }

    public void createDataBase() {

        boolean dbExist = checkDataBase();

        boolean dbTobeCreated = true;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        if (dbExist) {
            //do nothing if database version same
            SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
            ILog.d(TAG, "Database name " + database.getPath()+" and version " + database.getVersion());
            dbTobeCreated = false;

            if (database.getVersion() > preferences.getInt(mDbName + PreferenceUtils.DB_VERSION,
                    0)) {
                ILog.d(TAG, "Version mismatch and greater");
                if (mContext.deleteDatabase(mDbName)) {
                    ILog.d(TAG, "Database deleted");
                    dbTobeCreated = true;
                }
            }

        }

        if(dbTobeCreated) {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            mDatabaseHelper.getReadableDatabase();

            try {
                copyDataBase();
                ILog.d(TAG, "Database created");
                preferences.edit().putInt(mDbName + PreferenceUtils.DB_VERSION, mDbVersion).commit();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        File database = mContext.getDatabasePath(mDbName);
        return database.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(mDbName);

        // Path to the just created empty db
        String outFileName = DatabaseUtils.DB_PATH + mDbName;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
}
