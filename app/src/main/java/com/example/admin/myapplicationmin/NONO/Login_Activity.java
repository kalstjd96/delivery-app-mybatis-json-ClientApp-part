package com.example.admin.myapplicationmin.NONO;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.Data;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    private EditText edit_id;
    private EditText edit_pw;
    private Button login_btn;


    private NetworkTask networkTask = new NetworkTask();

    /*****************************************삭제 예정*************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        edit_id = (EditText) findViewById(R.id.login_id);
        edit_pw = (EditText) findViewById(R.id.login_pw);
        login_btn = (Button) findViewById(R.id.login_btn);
        final String URL = "/AndroidJSONServerOracle/AjsonServerDB2.jsp";

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String param = "id=" + edit_id.getText().toString().trim()+"&pw="+edit_pw.getText().toString().trim();
//                String sMessage = edit_id.getText().toString().trim();

                try {
//                    String result = andToweb.example(URL,param); //jsp 파일로 슝
//
//
//                    String[] jsonName = {"msg1","msg2","msg3"};
//                    String[][] parsedData = andToweb.jsonParserList(result, jsonName); // jsp파일에서 json받기

                    networkTask = new NetworkTask();
                    String UserID = edit_id.getText().toString().trim();
                    String UserPWD = edit_pw.getText().toString().trim();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Mapping","Login");
                    params.put("ID", UserID );
                    params.put("PWD", UserPWD);


                    /*networkTask.CHK = null;
                    networkTask.execute(params);*/
                    String msg = networkTask.execute(params).get();
                    Log.i("흠...", msg);

                    Gson gson = new Gson();

                    Data data = gson.fromJson(msg, Data.class);





                    if(data.getData1().equals("success")) {
                        Toast.makeText(getApplicationContext(),
                                "로그인 성공", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),
                                "로그인 실패", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

