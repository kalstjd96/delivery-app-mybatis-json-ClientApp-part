package com.example.admin.myapplicationmin.NONO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Model.LockerVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Locker_Activity extends AppCompatActivity {

    private LockerVO lockerVO;
    TextView txt_addr,txt_product;
    Button btn_open;

    /*****************************************삭제 예정*************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);
        setTitle("내 보관함확인");

//        txt_addr = (TextView)findViewById(R.id.txt_addr);
//        txt_product = (TextView)findViewById(R.id.txt_product);
//        btn_open = (Button) findViewById(R.id.btn_opne);
//
//        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
//
//        String User_MNO = auto.getString("inputMNO","회원번호오류");
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("Mapping", "Locker");
//        params.put("MNO", User_MNO);
//
//
//        NetworkTask networkTask = new NetworkTask();
//        String msg = null;
//        try {
//            msg = networkTask.execute(params).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("흠...", msg);
//
//        Gson gson = new Gson();
//        lockerVO = gson.fromJson(msg, LockerVO.class);
//        Log.i("테스트...", lockerVO.getLno());
//
//        if (lockerVO.getLno().equals("fail")){
//            Toast.makeText(Locker_Activity.this, "배송도착한 보관함이 없습니다.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Locker_Activity.this, Menu_Activity.class);
//            startActivity(intent);
//            finish();
//        } else {
//
//            txt_addr.setText(lockerVO.getLocker_addr());
//            txt_product.setText(lockerVO.getProduct());
//
//            btn_open.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Locker_Activity.this, LockerPass_Activity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//
//        }


    }
}
