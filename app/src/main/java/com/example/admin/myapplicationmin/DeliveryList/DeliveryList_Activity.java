package com.example.admin.myapplicationmin.DeliveryList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DeliveryList_Activity extends Fragment {

    ListView deliveryList;
    Button btn_re,btn_se;
    String CHK = "N";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_delivery_list, container, false);

        //setTitle("배송목록");

        deliveryList = (ListView)v.findViewById(R.id.deliverylist);
        btn_se = (Button)v.findViewById(R.id.btn_se);
        btn_re = (Button)v.findViewById(R.id.btn_re);




        deliveryListViewAdapter adapter = new deliveryListViewAdapter();
        deliveryList.setAdapter(adapter);

        SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        final String User_MNO = auto.getString("inputMNO","회원번호오류");

        btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_se.setEnabled(true);
                btn_re.setEnabled(false);
                btn_se.setBackgroundResource(R.drawable.blue_border);
                btn_re.setBackgroundResource(R.drawable.bluefill_border);
                btn_se.setTextColor(Color.parseColor("#0091EA"));
                btn_re.setTextColor(Color.parseColor("#FFFFFF"));


                deliveryListViewAdapter adapter1 = new deliveryListViewAdapter();
                deliveryList.setAdapter(adapter1);

                Map<String, String> params = new HashMap<String, String>();
                params.put("Mapping", "List");
                params.put("MNO", User_MNO);

                NetworkTask networkTask = new NetworkTask();
                String msg = null;
                try {
                    msg = networkTask.execute(params).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();

                TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};
                List<DeliveryVO> ListVO_List = gson.fromJson(msg, typeToken.getType());

                for (DeliveryVO deliveryVO : ListVO_List) {

                    adapter1.addItem(deliveryVO.getProduct_name()," | "+deliveryVO.getSe_name(),
                            deliveryVO.getWaybill_number(),deliveryVO.getProcess_date(),deliveryVO.getDelivery_type(),deliveryVO.getSe_req()) ;

                }
                CHK = "N";
                //Intent intent = new Intent(getActivity(), SE_DeliveryList_Activity.class);
                //startActivity(intent);
                //finish();

            }
        });

        btn_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_se.setEnabled(false);
                btn_re.setEnabled(true);

                btn_re.setBackgroundResource(R.drawable.blue_border);
                btn_se.setBackgroundResource(R.drawable.bluefill_border);
                btn_se.setTextColor(Color.parseColor("#FFFFFF"));
                btn_re.setTextColor(Color.parseColor("#0091EA"));

                deliveryListViewAdapter adapter2 = new deliveryListViewAdapter();
                deliveryList.setAdapter(adapter2);

                Map<String, String> params = new HashMap<String, String>();
                params.put("Mapping", "SE_List");
                params.put("MNO", User_MNO);

                NetworkTask networkTask = new NetworkTask();
                String msg = null;
                try {
                    msg = networkTask.execute(params).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();

                TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};
                List<DeliveryVO> ListVO_List = gson.fromJson(msg, typeToken.getType());

                for (DeliveryVO deliveryVO : ListVO_List) {

                    adapter2.addItem(deliveryVO.getProduct_name()," | "+deliveryVO.getSe_name(),
                            deliveryVO.getWaybill_number(),deliveryVO.getProcess_date(),deliveryVO.getDelivery_type(),deliveryVO.getSe_req()) ;

                }
                //Intent intent = new Intent(getActivity(), DeliveryList_Activity.class);
                //startActivity(intent);
                //finish();

                CHK = "Y";

            }
        });




        Map<String, String> params = new HashMap<String, String>();
        params.put("Mapping", "List");
        params.put("MNO", User_MNO);
        NetworkTask networkTask = new NetworkTask();
        String msg = null;
        try {
            msg = networkTask.execute(params).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};
        List<DeliveryVO> ListVO_List = gson.fromJson(msg, typeToken.getType());

        for (DeliveryVO deliveryVO : ListVO_List) {
            adapter.addItem(deliveryVO.getProduct_name()," | "+deliveryVO.getSe_name(),
                    deliveryVO.getWaybill_number(),deliveryVO.getProcess_date(),deliveryVO.getDelivery_type(),deliveryVO.getSe_req()) ;
        }
        deliveryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                DeliveryVO item = (DeliveryVO) parent.getItemAtPosition(position) ;

                String numStr =  item.getWaybill_number();

                Intent intent = new Intent(getActivity(), DeliveryDetail_Activity.class);
                intent.putExtra("WAYBILL_NUMBER",numStr);

                if(CHK.equals("Y")){
                    intent.putExtra("USER_CHK","OK");
                } else if (CHK.equals("N")) {
                    intent.putExtra("USER_CHK","NO");
                }
                startActivity(intent);

            }
        }) ;

        return v;

    }


}
