package com.twc.rca.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by TWC on 16-02-2018.
 */

public class MailUtils {

  /*  public static String getGoogleAccountMail(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context.getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return "";
    }*/

    public static ArrayList<String> getGoogleAccountMail(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        LinkedHashSet<String> unique_email_id = new LinkedHashSet<>();
        Account[] accounts = AccountManager.get(context.getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                unique_email_id.add(account.name);
                //return account.name;
            }
        }
        ArrayList<String> email_id=new ArrayList<>(unique_email_id);
        return email_id;
    }


    public static ArrayList<String> getAllGoogleAccountMailId(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context.getApplicationContext()).getAccounts();
        ArrayList<String> mylist = new ArrayList<String>();
        ArrayList<String> newList = null;
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                mylist.add(account.name);
                newList = new ArrayList(new HashSet(mylist));
            }
        }
        return newList;
    }

    public static boolean isValidMail(String mailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches();
    }
}
