package com.example.admin.myapplicationmin.NONO;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.DetailVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {
    private EditText name;
    private TextView numbers;
    private TextView address;
    private TextView phone;
    private Button btn1;
    private TextView idx;
    private TextView id;
    private NetworkTask networkTask = new NetworkTask();
    private DetailVO detailVO;


    /*****************************************삭제 예정*************************************/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        id =(TextView)findViewById(R.id.id);
        numbers = (TextView) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.names);
        address = (TextView) findViewById(R.id.address);
        phone = (TextView) findViewById(R.id.phone);
        idx = (TextView)findViewById(R.id.idx);


        Intent intents= getIntent();
        final String data =intents.getExtras().getString("str");
        idx.setText(data);
        Toast.makeText(getApplicationContext(),data,
                Toast.LENGTH_LONG).show();


        //전송
        networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();
        String ids = idx.getText().toString().trim();
        params.put("Mapping", "Detail");
        params.put("idx", ids);


        //받음
        String msg = null;
        try {
            msg = networkTask.execute(params).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("흠...", msg);

        //모델에 데이터 담기
        Gson gson = new Gson();
        detailVO = gson.fromJson(msg, DetailVO.class);
        Log.i("테스트이름...", detailVO.getIdx());


        numbers.setText(detailVO.getNumbers());
        name.setText(detailVO.getName());
        address.setText(detailVO.getAddress());
        phone.setText(detailVO.getPhone());
        id.setText(detailVO.getId());


    }



}