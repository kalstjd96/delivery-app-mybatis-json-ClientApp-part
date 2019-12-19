package com.example.admin.myapplicationmin.DeliveryList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SE_DeliveryList_Activity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_se__delivery_list, container, false);


        //setTitle("배송목록");

        ListView deliveryList = (ListView)v.findViewById(R.id.deliverylist);

        Button btn_re = (Button)v.findViewById(R.id.btn_re);

        btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DeliveryList_Activity.class);
                startActivity(intent);
                //finish();

            }
        });

        deliveryListViewAdapter adapter = new deliveryListViewAdapter();
        deliveryList.setAdapter(adapter);


        SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String User_MNO = auto.getString("inputMNO","회원번호오류");

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

            adapter.addItem(deliveryVO.getProduct_name(),deliveryVO.getSe_name(),
                    deliveryVO.getWaybill_number(),deliveryVO.getProcess_date(),deliveryVO.getDelivery_type(),deliveryVO.getSe_req()) ;

        }


        deliveryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                DeliveryVO item = (DeliveryVO) parent.getItemAtPosition(position) ;


                String se_nameStr = item.getSe_name();
                String p_nameStr = item.getProduct_name();
                String numStr =  item.getWaybill_number();
                String dateStr = item.getProcess_date();
                String typeStr = item.getDelivery_type();



                Intent intent = new Intent(getActivity(), DeliveryDetail_Activity.class);
                intent.putExtra("WAYBILL_NUMBER",numStr);
                intent.putExtra("USER_CHK","OK");
                startActivity(intent);

            }
        }) ;
        return v;
    }
}
