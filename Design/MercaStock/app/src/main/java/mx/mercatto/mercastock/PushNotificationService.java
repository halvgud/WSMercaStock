package mx.mercatto.mercastock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PushNotificationService extends GcmListenerService {
    public static ArrayList<String> Xrray;
    public static String idSucursal="0";
    public static String Sucursal="0";
    @Override
    public void onMessageReceived(String from, Bundle data) {
            String message = data.getString("message");
            //createNotification(mTitle, push_msg);
            // Configuracion.cat=data.getString("data");
            //String x="15,15";
            Log.d("data received",data.toString());
            Sucursal = data.getString("descripcionSucursal");
            idSucursal=data.getString("idSucursal");
            try {
                    Xrray = new ArrayList<String>();
                    JSONArray jar = new JSONArray(data.getString("data"));

                    for (int i=0;i<jar.length();i++){
                            Xrray.add(jar.get(i).toString());
                    }
                    HashSet<String> hashSet = new HashSet<String>();
                    hashSet.addAll(Xrray);
                    Xrray.clear();
                    Xrray.addAll(hashSet);
                    //Configuracion.cat=Xrray[1].toString();
            }catch(JSONException e){
                    Log.d("",e.getMessage());
            }
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_stat_mstockicon)
                            .setContentTitle("MercaStock! Sucursal:"+Sucursal)
                            .setContentText(message);
// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, Main.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(Main.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(300);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            // mNotificationManager.notify(mBuilder.build());
            mNotificationManager.notify(0, mBuilder.build());
            Log.d("mess", message);
    }
}
