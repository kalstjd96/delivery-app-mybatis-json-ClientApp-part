package com.example.admin.myapplicationmin.mymenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.DeliveryList.DeliveryDetail_Activity;
import com.example.admin.myapplicationmin.MainActivity;
import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.Model.ListVO;
import com.example.admin.myapplicationmin.Model.LockerVO;
import com.example.admin.myapplicationmin.Model.LoginVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MyMenu_Activity extends Fragment {

    TextView txt_pwd,txt_mno;
    TextView txt_id, txt_name, txt_phone, txt_addr1,txt_addr2,txt_addr3,txt_email,txt_birth,txt_locker_addr;
    Button btn_out,btn_lockeropen;
    String CHK;
    private LoginVO loginVO;

    List<DeliveryVO> ListVO_List;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_menu, container, false);



        txt_id = (TextView)v.findViewById(R.id.ID);
        txt_name = (TextView)v.findViewById(R.id.NAME);
        txt_phone = (TextView)v.findViewById(R.id.PHONE);
        txt_addr1 = (TextView)v.findViewById(R.id.ADDR1);
        txt_addr2 = (TextView)v.findViewById(R.id.ADDR2);
        txt_addr3 = (TextView)v.findViewById(R.id.ADDR3);
        txt_email = (TextView)v.findViewById(R.id.EMAIL);
        txt_birth = (TextView)v.findViewById(R.id.BIRTH);
        txt_locker_addr = (TextView)v.findViewById(R.id.locker_addr);
        btn_lockeropen =(Button)v.findViewById(R.id.btn_lockeropen);
        btn_out = (Button)v.findViewById(R.id.logout);

        LinearLayout locker_out = (LinearLayout)v.findViewById(R.id.locker_out);
        LinearLayout locker_in = (LinearLayout)v.findViewById(R.id.locker_in);
        ListView my_list = (ListView)v.findViewById(R.id.my_list);


//        txt_pwd = (TextView)v.findViewById(R.id.PWD);
//        txt_mno = (TextView)v.findViewById(R.id.MNO);



        myListViewAdapter adapter;

        // Adapter 생성
        adapter = new myListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        my_list.setAdapter(adapter);


        SharedPreferences auto = getActivity().getSharedPreferences("auto", MODE_PRIVATE);


        String User_ID = auto.getString("inputId","아이디오류");
        String User_PWD = auto.getString("inputPwd","비밀번호오류");
        String User_MNO = auto.getString("inputMNO","회원번호오류");


        Map<String, String> params = new HashMap<String, String>();
        params.put("Mapping", "MyMenu");
        params.put("MNO", User_MNO);

        NetworkTask networkTask = new NetworkTask();
        String msg = null;
        try {
            msg = networkTask.execute(params).get();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        loginVO = gson.fromJson(msg, LoginVO.class);



        txt_id.setText(loginVO.getId());
        txt_name.setText(loginVO.getName());
        txt_phone.setText(loginVO.getPhone());
        txt_addr1.setText(loginVO.getAddr1());
        txt_addr2.setText(loginVO.getAddr2());
        txt_addr3.setText(loginVO.getAddr3());
        txt_email.setText(loginVO.getEmail());
        txt_birth.setText(loginVO.getBirth());
//
//        txt_pwd.setText(User_PWD);
//        txt_mno.setText(User_MNO);


        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(), "로그아웃.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                //finish();

            }
        });


        //수정부분*******************************************************
        Map<String, String> params_L = new HashMap<String, String>();
        params_L.put("Mapping", "Locker");
        params_L.put("MNO", User_MNO);


        NetworkTask networkTask_L = new NetworkTask();
        String msg_L = null;
        try {
            msg_L = networkTask_L.execute(params_L).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("흠...", msg_L);

        //모델에 데이터 담기
        Gson gson_L = new Gson();

        TypeToken<List<DeliveryVO>> typeToken = new TypeToken<List<DeliveryVO>>() {};




        if (msg_L.equals("fail")){

            locker_out.setVisibility(View.VISIBLE);
            locker_in.setVisibility(View.GONE);

        } else {
            ListVO_List = gson_L.fromJson(msg_L, typeToken.getType());

            locker_out.setVisibility(View.GONE);
            locker_in.setVisibility(View.VISIBLE);
            for (DeliveryVO deliveryVO : ListVO_List) {
                adapter.addItem(deliveryVO.getProduct_name(),deliveryVO.getWaybill_number(),deliveryVO.getProcess_date()) ;

            }

            String a = "\n"+String.valueOf(adapter.getCount())+"개 물품 보관중";
            txt_locker_addr.setText(ListVO_List.get(0).getDelivery_location()+a);
        }

        btn_lockeropen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LockerPass_Activity.class);
                intent.putExtra("SNO",ListVO_List.get(0).getSno());
                startActivity(intent);

                //finish();
            }
        });

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                DeliveryVO item = (DeliveryVO) parent.getItemAtPosition(position) ;



                String numStr =  item.getWaybill_number();




                Intent intent = new Intent(getActivity(), DeliveryDetail_Activity.class);
                intent.putExtra("WAYBILL_NUMBER",numStr);


                intent.putExtra("USER_CHK","NO");


                startActivity(intent);

            }
        }) ;


    return v;


    }
}
