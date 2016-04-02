package com.mecanix.misael.mecanix;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by MISAEL on 25/11/2015.
 */
public class  CheckConnectionServices {

    public static Boolean connectionAvailable(Context context){
        if(conexionWifi(context)){
            Toast.makeText(context.getApplicationContext(), "Conexion Wifi disponible", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(conexionDatosMoviles(context)){
            Toast.makeText(context.getApplicationContext(), "Conexion Datos Moviles disponible", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Conexion no disponible",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    protected static  Boolean conexionWifi(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(info != null){
                if (info.isConnected()){
                    return true;
                }
            }
        }
        return false;
    }
    protected static Boolean conexionDatosMoviles(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(info != null){
                if(info.isConnected()){
                    return true;
                }
            }
        }
        return false;
    }
}
