package com.example.admin.myapplicationmin.Reservation;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.DeliveryList.SE_DeliveryList_Activity;
import com.example.admin.myapplicationmin.Menu_Activity;
import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Reservation_Activity extends Fragment {



    private NetworkTask networkTask = new NetworkTask();

    final int DIALOG_DATE = 1;
    int mYear, mMonth, mDay;

    EditText edit_product_name,edit_product_price;
    EditText edit_send_name, edit_send_phone, edit_send_addr1, edit_send_addr2, edit_send_addr3, edit_send_goods, edit_req;
    TextView text_send_name, text_send_phone, text_send_addr1, text_send_addr2, text_send_addr3, text_req;
    EditText edit_recv_name, edit_recv_phone, edit_recv_addr1, edit_recv_addr2, edit_recv_addr3;
    TextView text_recv_name, text_recv_phone, text_recv_addr1, text_recv_addr2, text_recv_addr3;
    TextView text_date,edit_product_fare_price;
    Spinner spinner,edit_product_wight,edit_product_fare;
    TextView text_product_company,text_product_fare_price,text_product_fare,text_product_wight,text_product_name,text_product_price;
    Button btn_date, btn_reservation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_reservation, container, false);


        //ActionBar ab = getSupportActionBar() ;
        //ab.setTitle("택배예약") ;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



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







        edit_send_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_send_name.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_send_name.setBackgroundResource(R.drawable.thin_border);

                    if(edit_send_name.getText().toString().length() > 0){
                        text_send_name.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_send_name.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_send_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_send_phone.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_send_phone.setBackgroundResource(R.drawable.thin_border);

                    if(edit_send_name.getText().toString().length() > 0){
                        text_send_phone.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_send_phone.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_send_addr1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_send_addr1.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_send_addr1.setBackgroundResource(R.drawable.thin_border);

                    if(edit_send_addr1.getText().toString().length() > 0){
                        text_send_addr1.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_send_addr1.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_send_addr2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_send_addr2.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_send_addr2.setBackgroundResource(R.drawable.thin_border);

                    if(edit_send_addr2.getText().toString().length() > 0){
                        text_send_addr2.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_send_addr2.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_send_addr3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_send_addr3.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_send_addr3.setBackgroundResource(R.drawable.thin_border);

                    if(edit_send_addr3.getText().toString().length() > 0){
                        text_send_addr3.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_send_addr3.setTextColor(Color.parseColor("#6C6C6C"));
                    }
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

        edit_recv_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_recv_name.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_recv_name.setBackgroundResource(R.drawable.thin_border);

                    if(edit_recv_name.getText().toString().length() > 0){
                        text_recv_name.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_recv_name.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_recv_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_recv_phone.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_recv_phone.setBackgroundResource(R.drawable.thin_border);

                    if(edit_recv_phone.getText().toString().length() > 0){
                        text_recv_phone.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_recv_phone.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_recv_addr1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_recv_addr1.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_recv_addr1.setBackgroundResource(R.drawable.thin_border);

                    if(edit_recv_addr1.getText().toString().length() > 0){
                        text_recv_addr1.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_recv_addr1.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_recv_addr2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_recv_addr2.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_recv_addr2.setBackgroundResource(R.drawable.thin_border);

                    if(edit_recv_addr2.getText().toString().length() > 0){
                        text_recv_addr2.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_recv_addr2.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_recv_addr3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_recv_addr3.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_recv_addr3.setBackgroundResource(R.drawable.thin_border);

                    if(edit_recv_addr3.getText().toString().length() > 0){
                        text_recv_addr3.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_recv_addr3.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_product_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_product_name.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_product_name.setBackgroundResource(R.drawable.thin_border);

                    if(edit_product_name.getText().toString().length() > 0){
                        text_product_name.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_product_name.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });

        edit_product_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_product_price.setBackgroundResource(R.drawable.blue_border);
                }else {
                    edit_product_price.setBackgroundResource(R.drawable.thin_border);

                    if(edit_product_price.getText().toString().length() > 0){
                        text_product_price.setTextColor(Color.parseColor("#0091EA"));
                    } else {
                        text_product_price.setTextColor(Color.parseColor("#6C6C6C"));
                    }
                }
            }
        });



        edit_product_wight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        edit_product_fare_price.setText("0");
                        text_product_wight.setTextColor(Color.parseColor("#6C6C6C"));
                        text_product_fare_price.setTextColor(Color.parseColor("#6C6C6C"));
                        break;
                    case 1:
                        edit_product_fare_price.setText("4000");
                        text_product_wight.setTextColor(Color.parseColor("#0091EA"));
                        text_product_fare_price.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 2:
                        edit_product_fare_price.setText("6000");
                        text_product_wight.setTextColor(Color.parseColor("#0091EA"));
                        text_product_fare_price.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 3:
                        edit_product_fare_price.setText("7000");
                        text_product_wight.setTextColor(Color.parseColor("#0091EA"));
                        text_product_fare_price.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 4:
                        edit_product_fare_price.setText("8000");
                        text_product_wight.setTextColor(Color.parseColor("#0091EA"));
                        text_product_fare_price.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        text_product_company.setTextColor(Color.parseColor("#6C6C6C"));
                        break;
                    case 1:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 2:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 3:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 4:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 5:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 6:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 7:
                        text_product_company.setTextColor(Color.parseColor("#0091EA"));
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_product_fare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        text_product_fare.setTextColor(Color.parseColor("#6C6C6C"));
                        break;
                    case 1:
                        text_product_fare.setTextColor(Color.parseColor("#0091EA"));
                        break;
                    case 2:
                        text_product_fare.setTextColor(Color.parseColor("#0091EA"));
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (send_name.length() == 0) {
                    Toast.makeText(getActivity(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                    edit_send_name.requestFocus();

                    //키보드 보이게 하는 부분
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                } else if (send_phone.length() == 0) {
                    Toast.makeText(getActivity(), "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                    edit_send_phone.requestFocus();

                    //키보드 보이게 하는 부분
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                } else if (send_addr1.length() == 0) {
                    Toast.makeText(getActivity(), "우편번호를 입력하세요.", Toast.LENGTH_LONG).show();
                    edit_send_addr1.requestFocus();

                    //키보드 보이게 하는 부분
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if (send_addr2.length() == 0) {
                    Toast.makeText(getActivity(), "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                    edit_send_addr2.requestFocus();

                    //키보드 보이게 하는 부분
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if (send_addr3.length() == 0) {
                    Toast.makeText(getActivity(), "상세주소를 입력하세요.", Toast.LENGTH_LONG).show();
                    edit_send_addr3.requestFocus();

                    //키보드 보이게 하는 부분
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if (send_company.equals("선택해주세요.")) {
                    Toast.makeText(getActivity(), "배송회사를 선택해주세요.", Toast.LENGTH_LONG).show();
                    spinner.requestFocus();
                }

                else {
                    try {

                        /*String Param = "send_name="+ send_name +"&send_tel="+ send_tel +"&send_addr1="+ send_addr1 +
                                        "&send_addr2=" + send_addr2 + "&send_addr3=" + send_addr3 + "&send_goods=" + send_goods + "&send_date="+ send_date + "&send_company=" + send_company +
                                        "&recv_name=" + recv_name + "&recv_tel=" + recv_tel + "&recv_addr1=" + recv_addr1 + "&recv_addr2=" + recv_addr2 + "&recv_addr3=" + recv_addr3 + "&chk=join";

                        String result = andToweb.example(URL, Param); //jsp 파일로 슝*/



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
            }
        });


    return v;


    }




}
