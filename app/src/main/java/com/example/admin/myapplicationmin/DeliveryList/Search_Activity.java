package com.example.admin.myapplicationmin.DeliveryList;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;

import java.util.HashMap;
import java.util.Map;

public class Search_Activity extends Fragment {

    EditText edt_num;
    Button btn_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search, container, false);


        edt_num = (EditText)v.findViewById(R.id.edt_num);
        btn_search = (Button)v.findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String WAYBILL_NUMBER = edt_num.getText().toString().trim();

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



                if (msg.equals("fail")) {
                    edt_num.setText("");
                    Toast.makeText(getActivity(), "존재하지않는 운송장 번호입니다.", Toast.LENGTH_SHORT).show();

                } else {
                    edt_num.setText("");
                    Intent intent = new Intent(getActivity(), DeliveryDetail_Activity.class);
                    intent.putExtra("WAYBILL_NUMBER",WAYBILL_NUMBER);
                    intent.putExtra("USER_CHK","NO");
                    startActivity(intent);
                }


            }
        });

        return v;
    }
}
