package com.example.projectver3.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class AppUltil {
    public static boolean isNetWorkAvailable(Context context){
        if (context == null){
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null){
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            Network network = connectivityManager.getActiveNetwork();
            if (network == null){
                return false;
            }

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    || (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));

        }
        else
        {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

    }


}
