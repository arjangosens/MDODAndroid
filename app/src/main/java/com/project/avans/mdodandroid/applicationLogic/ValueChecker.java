package com.project.avans.mdodandroid.applicationLogic;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueChecker {
    private static final String VALUECHECKERSTRING = "ValueChecker";

    public static boolean checkPassword(String newPassword, String confirmPassword) {
        Pattern pwRegex =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

        Matcher matcher = pwRegex.matcher(newPassword);

        boolean pwRegexResult = matcher.find();

        if (pwRegexResult) {

            if (newPassword.equals(confirmPassword)) {
                Log.i(VALUECHECKERSTRING, "checkPassword() passwords are equal");
                return true;

            } else {
                Log.i(VALUECHECKERSTRING, "checkPassword() passwords are NOT equal");
                return false;

            }

        } else {
            Log.i(VALUECHECKERSTRING, "checkPassword() password is invalid");
            return false;
        }
    }

    public static boolean checkPassword(String currentPassword, String newPassword, String confirmPassword) {

        //TODO: Modify this method so that it checks if currentPassword is correct

        Pattern pwRegex =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

        Matcher matcher = pwRegex.matcher(newPassword);

        boolean pwRegexResult = matcher.find();

        if (pwRegexResult) {

            if (newPassword.equals(confirmPassword)) {
                Log.i(VALUECHECKERSTRING, "checkPassword() passwords are equal");
                return true;

            } else {
                Log.i(VALUECHECKERSTRING, "checkPassword() passwords are NOT equal");
                return false;

            }

        } else {
            Log.i(VALUECHECKERSTRING, "checkPassword() password is invalid");
            return false;
        }
    }

    public static boolean checkEmail(String email) {
        Pattern emailRegex =
                Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailRegex .matcher(email);

        boolean result = matcher.find();
        Log.i("RegisterActivity", "checkEmail email is " + result);

        return result;
    }
}
