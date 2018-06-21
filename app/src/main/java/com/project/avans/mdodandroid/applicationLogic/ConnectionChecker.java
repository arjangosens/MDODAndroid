package com.project.avans.mdodandroid.applicationLogic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecker {

    public static Boolean CheckCon(Context context){

        try{
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected){
                return false;
            }
            else{
                return true;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }

    }
}
