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

    public static boolean checkCurrentPassword(String currentPassword) {
        return true;
    }

    public static boolean checkNewPasswordFormat(String newPassword) {
        Pattern pwRegex =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

        Matcher matcher = pwRegex.matcher(newPassword);

        return matcher.find();
    }

    public static boolean checkConfirmMatchesNewPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            Log.i(VALUECHECKERSTRING, "checkPassword() passwords are equal");
            return true;

        } else {
            Log.i(VALUECHECKERSTRING, "checkPassword() passwords are NOT equal");
            return false;
        }
    }

    public static boolean checkPassword(String currentPassword, String newPassword, String
            confirmPassword) {

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

        Matcher matcher = emailRegex.matcher(email);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkEmail() email is " + result);

        return result;
    }

    public static boolean checkPhoneNumber (String phoneNumber) {
        Pattern phoneNrRegex = Pattern.compile("^\\+?\\d{6,13}");
        Matcher matcher = phoneNrRegex.matcher(phoneNumber);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkPhoneNumber() phoneNumber is " + result);
        return result;
    }

    public static boolean checkZipCode(String zipCode) {
        Pattern zipCodeRegex = Pattern.compile("^\\d{4}\\s?[A-Za-z]{2}");
        Matcher matcher = zipCodeRegex.matcher(zipCode);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkZipCode() zipCode is " + result);
        return result;
    }

    public static boolean checkCity(String city) {
        Pattern cityRegex = Pattern.compile("^[a-zA-Z]+([?:\\s-'][a-zA-Z]+)*$");
        Matcher matcher = cityRegex.matcher(city);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkCity() city is " + result);
        return result;
    }

    public static boolean checkName(String name) {
        Pattern nameRegex = Pattern.compile("[A-Za-z\\-]{2,50}$");
        Matcher matcher = nameRegex.matcher(name);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkName() name is " + result);
        return result;
    }

    public static boolean checkInsertion(String insertion) {
        boolean result = false;

        if (insertion.isEmpty()) {
            result = true;

        } else {
            Pattern insertionRegex = Pattern.compile("^[A-Za-z]{2,8}(\\s[A-Z-a-z]{2,8})*");
            Matcher matcher = insertionRegex.matcher(insertion);

            result = matcher.find();
            Log.i("ValueChecker", "checkInsertion() insertion is " + result);
        }

        return result;
    }

    public static boolean checkAddress(String address) {
        Pattern addressRegex = Pattern.compile("([A-Za-z'\\-]+\\s)+\\d+([A-Z-a-z]*)");
        Matcher matcher = addressRegex.matcher(address);

        boolean result = matcher.find();
        Log.i("ValueChecker", "checkAddress() address is " + result);
        return result;
    }
}
