package com.example.admin.myapplicationmin.NONO;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.myapplicationmin.Model.AddressVO;
import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.R;

public class Res_chk_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_chk);
        setTitle("택배예약내용확인");

        Intent intent = getIntent();
        DeliveryVO deliveryVO = (DeliveryVO) intent.getSerializableExtra("Reservation");
        Log.i("테스트1...", deliveryVO.getWaybill_number());
        Log.i("테스트2...", deliveryVO.getDno());
    }
}
