package com.example.admin.myapplicationmin.DeliveryList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.example.admin.myapplicationmin.Reservation.Reservation_Locker_Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryDetail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);
        setTitle("조회결과");

        TextView text_num = (TextView)findViewById(R.id.text_num);
        TextView text_product_name = (TextView)findViewById(R.id.text_product_name);
        TextView text_se_name = (TextView)findViewById(R.id.text_se_name);
        TextView text_re_name = (TextView)findViewById(R.id.text_re_name);
        ListView detaillist = (ListView) findViewById(R.id.detaillist);

        //택배예약시 사물함선택 레이아웃
        LinearLayout Linear_select = (LinearLayout)findViewById(R.id.Linear_select);
        Button btn_select = (Button)findViewById(R.id.btn_select);

        //바로 사물함선택숨기기
        Linear_select.setVisibility(View.GONE);



        Intent intent = getIntent();
        final String WAYBILL_NUMBER = intent.getExtras().getString("WAYBILL_NUMBER");
        String USER_CHK = intent.getExtras().getString("USER_CHK");



        deliveryDetailViewAdapter adapter = new deliveryDetailViewAdapter();
        detaillist.setAdapter(adapter);

        Map<String, String> params = new HashMap<String, String>();
        params.put("Mapping", "Detail");
        params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);

        NetworkTask networkTask = new NetworkTask();
        String msg = null;
        try {
            msg = networkTask.execute(params).get();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (msg.equals("fail")){

            Toast.makeText(DeliveryDetail_Activity.this, "존재하지않는 운송장 번호입니다.", Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(DeliveryDetail_Activity.this, Menu_Activity.class);
            intent1.putExtra("Fragment","Search");
            startActivity(intent1);
            finish();

        } else {
            Gson gson = new Gson();

            TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};
            List<DeliveryVO> ListVO_List = gson.fromJson(msg, typeToken.getType());

            text_num.setText(ListVO_List.get(0).getWaybill_number());
            text_product_name.setText(ListVO_List.get(0).getProduct_name());
            text_se_name.setText(ListVO_List.get(0).getSe_name());
            text_re_name.setText(ListVO_List.get(0).getRe_name());
            Log.i("테스트 배송타입...", ListVO_List.get(0).getDelivery_type());

            if(ListVO_List.get(0).getDelivery_type().equals("0") && USER_CHK.equals("OK")){
                Linear_select.setVisibility(View.VISIBLE);
            }

            for (DeliveryVO deliveryVO : ListVO_List) {

                adapter.addItem(deliveryVO.getDelivery_information(),deliveryVO.getProcess_date());

            }

            btn_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(DeliveryDetail_Activity.this, Reservation_Locker_Activity.class);
                    intent.putExtra("WAYBILL_NUMBER",WAYBILL_NUMBER);
                    startActivity(intent);
                    finish();

                }
            });
        }



    }
}
