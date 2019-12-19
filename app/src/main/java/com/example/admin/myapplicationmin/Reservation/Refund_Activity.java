package com.example.admin.myapplicationmin.Reservation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.DeliveryList.SE_DeliveryList_Activity;
import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Model.AddressVO;
import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Refund_Activity extends Fragment {

    private AddressVO addressVO;
    /*private NetworkTask networkTask = new NetworkTask();*/

    final int DIALOG_DATE = 1;
    int mYear, mMonth, mDay;


    EditText edit_num,edit_product_name,edit_product_price;
    EditText edit_send_name, edit_send_phone, edit_send_addr1, edit_send_addr2, edit_send_addr3, edit_send_goods, edit_req;
    TextView text_send_name, text_send_phone, text_send_addr1, text_send_addr2, text_send_addr3, text_req;
    EditText edit_recv_name, edit_recv_phone, edit_recv_addr1, edit_recv_addr2, edit_recv_addr3;
    TextView text_recv_name, text_recv_phone, text_recv_addr1, text_recv_addr2, text_recv_addr3;
    TextView text_date,edit_product_fare_price;
    Spinner spinner,edit_product_wight,edit_product_fare;
    TextView text_product_company,text_product_fare_price,text_product_fare,text_product_wight,text_product_name,text_product_price;
    Button btn_date, btn_reservation,btn_num;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_refund, container, false);
        //setContentView(R.layout.activity_refund);
        //setTitle("환불신청");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        /*운송번호조회*/
        edit_num = (EditText)v.findViewById(R.id.edit_num);
        btn_num = (Button) v.findViewById(R.id.btn_num);

        /*보내는쪽*/
        edit_send_name = (EditText)v.findViewById(R.id.edit_send_name);
        text_send_name = (TextView)v.findViewById(R.id.text_send_name);

        edit_send_phone = (EditText)v.findViewById(R.id.edit_send_phone);
        text_send_phone = (TextView) v.findViewById(R.id.text_send_phone);

        edit_send_addr1 = (EditText)v.findViewById(R.id.edit_send_addr1);
        text_send_addr1 = (TextView) v.findViewById(R.id.text_send_addr1);

        edit_send_addr2 = (EditText)v.findViewById(R.id.edit_send_addr2);
        text_send_addr2 = (TextView) v.findViewById(R.id.text_send_addr2);

        edit_send_addr3 = (EditText)v.findViewById(R.id.edit_send_addr3);
        text_send_addr3 = (TextView) v.findViewById(R.id.text_send_addr3);

        edit_req = (EditText)v.findViewById(R.id.edit_req);
        text_req = (TextView) v.findViewById(R.id.text_req);
        /*받는쪽*/
        edit_recv_name = (EditText)v.findViewById(R.id.edit_recv_name);
        text_recv_name = (TextView)v.findViewById(R.id.text_recv_name);

        edit_recv_phone = (EditText)v.findViewById(R.id.edit_recv_phone);
        text_recv_phone = (TextView)v.findViewById(R.id.text_recv_phone);

        edit_recv_addr1 = (EditText)v.findViewById(R.id.edit_recv_addr1);
        text_recv_addr1 = (TextView)v.findViewById(R.id.text_recv_addr1);

        edit_recv_addr2 = (EditText)v.findViewById(R.id.edit_recv_addr2);
        text_recv_addr2 = (TextView)v.findViewById(R.id.text_recv_addr2);

        edit_recv_addr3 = (EditText)v.findViewById(R.id.edit_recv_addr3);
        text_recv_addr3 = (TextView)v.findViewById(R.id.text_recv_addr3);


        /*상품*/
        spinner = (Spinner) v.findViewById(R.id.spinner); // 택배
        text_product_company = (TextView)v.findViewById(R.id.text_product_company);

        edit_product_name = (EditText)v.findViewById(R.id.edit_product_name);
        text_product_name = (TextView)v.findViewById(R.id.text_product_name);

        edit_product_price = (EditText)v.findViewById(R.id.edit_product_price);
        text_product_price = (TextView)v.findViewById(R.id.text_product_price);

        edit_product_fare_price = (TextView)v.findViewById(R.id.edit_product_fare_price);
        text_product_fare_price = (TextView)v.findViewById(R.id.text_product_fare_price);

        edit_product_wight = (Spinner)v.findViewById(R.id.edit_product_wight);
        text_product_wight = (TextView)v.findViewById(R.id.text_product_wight);

        edit_product_fare = (Spinner)v.findViewById(R.id.edit_product_fare) ;
        text_product_fare = (TextView)v.findViewById(R.id.text_product_fare);


        btn_reservation = (Button)v.findViewById(R.id.btn_reservation);




        /*택배회사 스피너*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*운송무게 스피너*/
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.wight_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_product_wight.setAdapter(adapter2);

        /*운송구분 스피너*/
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(), R.array.fare_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_product_fare.setAdapter(adapter3);

        spinner.setEnabled(false);
        edit_product_wight.setEnabled(false);
        edit_product_fare.setEnabled(false);




        btn_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String WAYBILL_NUMBER = edit_num.getText().toString().trim();


                    try {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Mapping", "Refund");
                        params.put("WAYBILL_NUMBER", WAYBILL_NUMBER);

                        NetworkTask networkTask = new NetworkTask();
                        String msg = networkTask.execute(params).get();
                        Log.i("흠...", msg);

                        Gson gson = new Gson();
                        DeliveryVO deliveryVO = gson.fromJson(msg, DeliveryVO.class);

                        if (deliveryVO == null){
                            Toast.makeText(getActivity(), "존재하지않는 운송장 번호입니다.", Toast.LENGTH_SHORT).show();
                        }else {





                            /*보내는쪽*/
                            edit_send_name.setText(deliveryVO.getRe_name());
                            edit_send_phone.setText(deliveryVO.getRe_phone());
                            edit_send_addr1.setText(deliveryVO.getRe_addr1());
                            edit_send_addr2.setText(deliveryVO.getRe_addr2());
                            edit_send_addr3.setText(deliveryVO.getRe_addr3());

                            /*받는쪽*/
                            edit_recv_name.setText(deliveryVO.getSe_name());
                            edit_recv_phone.setText(deliveryVO.getSe_phone());
                            edit_recv_addr1.setText(deliveryVO.getSe_addr1());
                            edit_recv_addr2.setText(deliveryVO.getSe_addr2());
                            edit_recv_addr3.setText(deliveryVO.getSe_addr3());

                            /*상품쪽*/

                            edit_product_name.setText(deliveryVO.getProduct_name());
                            edit_product_price.setText(deliveryVO.getProduct_price());
                            edit_product_fare_price.setText(deliveryVO.getProduct_fare_price());

                            switch (deliveryVO.getProduct_fare_price()) {
                                case "4000":
                                    edit_product_wight.setSelection(1);
                                    break;
                                case "6000":
                                    edit_product_wight.setSelection(2);
                                    break;
                                case "7000":
                                    edit_product_wight.setSelection(3);
                                    break;
                                case "8000":
                                    edit_product_wight.setSelection(4);
                                    break;
                                default:
                                    Toast.makeText(getActivity(),
                                            "운임무게 오류오류!!!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }


                            String fare = deliveryVO.getProduct_fare();
                            switch (fare) {
                                case "1":
                                    edit_product_fare.setSelection(1);
                                    break;
                                case "2":
                                    edit_product_fare.setSelection(2);
                                    break;
                                default:
                                    Toast.makeText(getActivity(),
                                            "운임구분 오류오류!!!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            String company = deliveryVO.getCno();
                            switch (company) {
                                case "1":
                                    spinner.setSelection(1);
                                    break;
                                case "2":
                                    spinner.setSelection(2);
                                    break;
                                case "3":
                                    spinner.setSelection(3);
                                    break;
                                case "4":
                                    spinner.setSelection(4);
                                    break;
                                case "5":
                                    spinner.setSelection(5);
                                    break;
                                case "6":
                                    spinner.setSelection(6);
                                    break;
                                case "7":
                                    spinner.setSelection(7);
                                    break;
                                default:
                                    Toast.makeText(getActivity(),
                                            "회사 오류오류!!!",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



        });


        edit_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_num.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_num.setBackgroundResource(R.drawable.thin_border);
                }
            }
        });

        edit_req.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_req.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_req.setBackgroundResource(R.drawable.thin_border);

                    if(edit_req.getText().toString().length() > 0){
                        text_req.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_req.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });




        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*보내는쪽 값*/
                String send_name = edit_send_name.getText().toString().trim();
                String send_phone = edit_send_phone.getText().toString().trim();
                String send_addr1 = edit_send_addr1.getText().toString().trim();
                String send_addr2 = edit_send_addr2.getText().toString().trim();
                String send_addr3 = edit_send_addr3.getText().toString().trim();
                String send_seq = edit_req.getText().toString().trim();
                if(send_seq.length() == 0){
                    send_seq = "없음";
                }


                /*받는쪽 값*/
                String recv_name = edit_recv_name.getText().toString().trim();
                String recv_phone = edit_recv_phone.getText().toString().trim();
                String recv_addr1 = edit_recv_addr1.getText().toString().trim();
                String recv_addr2 = edit_recv_addr2.getText().toString().trim();
                String recv_addr3 = edit_recv_addr3.getText().toString().trim();

                /*상품*/
                String product_name = edit_product_name.getText().toString().trim();
                String product_price = edit_product_price.getText().toString().trim();
                String product_fare_price = edit_product_fare_price.getText().toString().trim();

                String product_fare;


                switch (edit_product_fare.getSelectedItem().toString()){ //운임구분
                    case "선불":
                        product_fare = "1";
                        break;

                    case "착불":
                        product_fare = "2";
                        break;

                    default:
                        product_fare = "error";
                        break;
                }

                String send_company;

                switch (spinner.getSelectedItem().toString()){ //택배
                    case "CJ대한통운":
                        send_company = "1";
                        break;

                    case "로젠택배":
                        send_company = "2";
                        break;

                    default:
                        send_company = "error";
                        break;
                }



                    try {

                        //파라메터값
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Mapping","Reservation");
                        params.put("se_name", send_name );
                        params.put("se_phone", send_phone );
                        params.put("se_addr1", send_addr1 );
                        params.put("se_addr2", send_addr2 );
                        params.put("se_addr3", send_addr3 );
                        params.put("se_req", send_seq);


                        params.put("re_name", recv_name );
                        params.put("re_phone", recv_phone );
                        params.put("re_addr1", recv_addr1 );
                        params.put("re_addr2", recv_addr2 );
                        params.put("re_addr3", recv_addr3 );

                        params.put("product_name", product_name );
                        params.put("product_price", product_price );
                        params.put("product_fare", product_fare );
                        params.put("product_fare_price", product_fare_price );
                        params.put("cno", send_company );





                        NetworkTask networkTask = new NetworkTask();
                        String msg = networkTask.execute(params).get();
                        Log.i("흠...", msg);






                        //모델에 데이터 담기
                        Gson gson = new Gson();
                        DeliveryVO deliveryVO = gson.fromJson(msg, DeliveryVO.class);
                        Log.i("테스트번호...", deliveryVO.getWaybill_number());
                        Toast.makeText(getActivity(), "택배가 예약되었습니다. 사물함을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Menu_Activity.class);
                        intent.putExtra("Fragment","List");
                        startActivity(intent);
                        //finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

    return v;


    }

}
