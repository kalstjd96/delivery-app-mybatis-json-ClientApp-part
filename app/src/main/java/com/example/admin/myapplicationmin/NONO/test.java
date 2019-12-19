package com.example.admin.myapplicationmin.NONO;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class test extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private TextView tagDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tagDesc = (TextView)findViewById(R.id.tagDesc);

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
            tagDesc.setText("TagID: " + toHexString(tagId));

            Map<String, String> params = new HashMap<String, String>();
            params.put("Mapping", "nfc");
            params.put("TagID", toHexString(tagId));
            params.put("UserID", "1");

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
