package com.example.admin.myapplicationmin.mymenu;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Model.LockerVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LockerNFC_Activity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    TextView txt_addr2,txt_addr3,txt_no;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_nfc);
        setTitle("NFC 보관함열기");

        Intent intent1 = getIntent();
        String SNO = intent1.getExtras().getString("SNO");

        txt_addr2 = (TextView)findViewById(R.id.txt_addr2);
        txt_addr3 = (TextView)findViewById(R.id.txt_addr3);
        txt_no = (TextView)findViewById(R.id.txt_no);



        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NetworkTask networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "LockerDetail");
        params.put("SNO", SNO);


        String msg = null;

        try {
            msg = networkTask.execute(params).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("흠1...", msg);

        //모델에 데이터 담기
        Gson gson = new Gson();

        LockerVO lockerVO = gson.fromJson(msg, LockerVO.class);

        txt_addr2.setText(lockerVO.getL_addr2());
        txt_addr3.setText(lockerVO.getL_addr3());
        txt_no.setText(lockerVO.getDetail_no()+"번 사물함");
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
            SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
            String User_MNO = auto.getString("inputMNO","회원번호오류");

            byte[] tagId = tag.getId();


            Map<String, String> params = new HashMap<String, String>();
            params.put("Mapping", "nfc");
            params.put("TagID", toHexString(tagId));
            params.put("UserID", User_MNO);

            NetworkTask networkTask = new NetworkTask();
            String msg = null;

            try {
                msg = networkTask.execute(params).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            Log.i("흠...", msg);


            Intent intent2 = new Intent(LockerNFC_Activity.this, Menu_Activity.class);
            intent2.putExtra("Fragment","Menu");
            startActivity(intent2);
            finish();
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
