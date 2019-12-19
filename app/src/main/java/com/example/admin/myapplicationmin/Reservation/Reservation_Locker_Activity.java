package com.example.admin.myapplicationmin.Reservation;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.myapplicationmin.DeliveryList.DeliveryList_Activity;
import com.example.admin.myapplicationmin.DeliveryList.SE_DeliveryList_Activity;
import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.example.admin.myapplicationmin.mymenu.LockerNFC_Activity;
import com.example.admin.myapplicationmin.mymenu.MyMenu_Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Reservation_Locker_Activity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    String WAYBILL_NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_locker);
        setTitle("보관함 선택");

        Intent intent1 = getIntent();
        WAYBILL_NUMBER = intent1.getExtras().getString("WAYBILL_NUMBER");


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    }

    @Override
    protected void onPause() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {


            byte[] tagId = tag.getId();


            Map<String, String> params = new HashMap<String, String>();
            params.put("Mapping", "se_nfc");
            params.put("TagID", toHexString(tagId));
            params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);

            NetworkTask networkTask = new NetworkTask();
            String msg = null;

            try {
                msg = networkTask.execute(params).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Log.i("흠...", msg);

            if (msg.equals("fail")){
                Toast.makeText(Reservation_Locker_Activity.this, "이미사용하고있는 보관함입니다. \n 다른보관함을 선택해주세요.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Reservation_Locker_Activity.this, "열렸습니다.", Toast.LENGTH_LONG).show();

                Intent intent2 = new Intent(Reservation_Locker_Activity.this, Menu_Activity.class);
                intent2.putExtra("Fragment","List");
                startActivity(intent2);
                finish();
            }


        }
    }


    public static final String CHARS = "0123456789ABCDEF";

    public static String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            sb.append(CHARS.charAt((data[i] >> 4) & 0x0F))
                    .append(CHARS.charAt(data[i] & 0x0F));
        }
        return sb.toString();
    }
}
