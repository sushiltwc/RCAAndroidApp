package com.twc.rca;

import android.app.Application;
import android.content.Context;

import com.twc.rca.database.ApplicantHelper;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.database.DatabaseCreater;
import com.twc.rca.database.LanguageHelper;
import com.twc.rca.database.MaritalHelper;
import com.twc.rca.database.OrderHelper;
import com.twc.rca.database.PassportTypeHelper;
import com.twc.rca.database.ProfessionHelper;
import com.twc.rca.database.ReligionHelper;

/**
 * Created by Sushil on 15-03-2018.
 */

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;

    public MyApplication() {
        super();
    }

    public void onCreate() {
        mInstance = this;
        super.onCreate();
        createDatabases();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void createDatabases() {
        CountryHelper countryHelper = CountryHelper.getInstance(this);
        LanguageHelper languageHelper = LanguageHelper.getInstance(this);
        MaritalHelper maritalHelper = MaritalHelper.getInstance(this);
        PassportTypeHelper passportTypeHelper = PassportTypeHelper.getInstance(this);
        ProfessionHelper professionHelper = ProfessionHelper.getInstance(this);
        ReligionHelper religionHelper = ReligionHelper.getInstance(this);
        OrderHelper orderHelper = OrderHelper.getInstance(this);
        ApplicantHelper applicantHelper = ApplicantHelper.getInstance(this);

        new DatabaseCreater(this, countryHelper, countryHelper.DB_NAME, countryHelper.DB_VERSION).createDataBase();
        new DatabaseCreater(this, languageHelper, languageHelper.DB_NAME, languageHelper.DB_VERSION).createDataBase();
        new DatabaseCreater(this, maritalHelper, maritalHelper.DB_NAME, maritalHelper.DB_VERSION).createDataBase();
        new DatabaseCreater(this, passportTypeHelper, passportTypeHelper.DB_NAME, passportTypeHelper.DB_VERSION).createDataBase();
        new DatabaseCreater(this, professionHelper, professionHelper.DB_NAME, professionHelper.DB_VERSION).createDataBase();
        new DatabaseCreater(this, religionHelper, religionHelper.DB_NAME, religionHelper.DB_VERSION).createDataBase();
      //  new DatabaseCreater(this, orderHelper, OrderHelper.DB_NAME, OrderHelper.DB_VERSION).createDataBase();
      //  new DatabaseCreater(this, applicantHelper, ApplicantHelper.DB_NAME, ApplicantHelper.DB_VERSION).createDataBase();

        countryHelper.openDataBase();
        languageHelper.openDataBase();
        maritalHelper.openDataBase();
        passportTypeHelper.openDataBase();
        professionHelper.openDataBase();
        religionHelper.openDataBase();
        orderHelper.openDataBase();
        applicantHelper.openDataBase();
    }
}
