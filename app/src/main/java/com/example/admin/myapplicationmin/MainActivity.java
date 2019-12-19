package com.example.admin.myapplicationmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.LoginVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LoginVO loginVO;


    EditText id, pwd;
    Button btn,loginjoin;
    String loginId, loginPwd;
    ImageView imgid,imgpwd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("로그인");




        id = (EditText)findViewById(R.id.inputId);
        pwd = (EditText)findViewById(R.id.inputPwd);
        btn = (Button)findViewById(R.id.loginBtn);
        loginjoin = (Button)findViewById(R.id.loginjoin);

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        final String token =  pref.getString("token", "0");



        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    id.setBackgroundResource(R.drawable.blue_border);
                }else {
                    id.setBackgroundResource(R.drawable.thin_border);
                }
            }
        });

        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    pwd.setBackgroundResource(R.drawable.blue_border);
                }else {
                    pwd.setBackgroundResource(R.drawable.thin_border);
                }
            }
        });

        //MainActivity로 들어왔을 때 loginId와 loginPwd값을 가져와서 null이 아니면
        //값을 가져와 id가 부르곰이고 pwd가 네이버 이면 자동적으로 액티비티 이동.
        if(loginId !=null && loginPwd != null) {


            Intent intent = new Intent(MainActivity.this, Menu_Activity.class);
            intent.putExtra("Fragment","List");
            startActivity(intent);
            finish();

        }
        //id와 pwd가 null이면 Mainactivity가 보여짐.
        else if(loginId == null && loginPwd == null){
            loginjoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String User_id = id.getText().toString().trim();
                    String User_pass = pwd.getText().toString().trim();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Mapping","Login");
                    params.put("User_id", User_id );
                    params.put("User_pass", User_pass );
                    params.put("token",token);

                    NetworkTask networkTask = new NetworkTask();
                    String msg = null;
                    try {
                        msg = networkTask.execute(params).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("흠...", msg);

                    Gson gson = new Gson();
                    loginVO = gson.fromJson(msg, LoginVO.class);


                    if (loginVO.getMno().equals("fail")){
                        id.setText("");
                        pwd.setText("");
                        Toast.makeText(MainActivity.this, "회원정보가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if (User_id.equals(loginVO.getId()) && User_pass.equals(loginVO.getPass())){
                        Log.i("테스트아이디...", loginVO.getId());
                        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
                        //아이디가 '부르곰'이고 비밀번호가 '네이버'일 경우 SharedPreferences.Editor를 통해
                        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputId", loginVO.getId());
                        autoLogin.putString("inputPwd", loginVO.getPass());
                        autoLogin.putString("inputMNO", loginVO.getMno());
                        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ

                        autoLogin.commit();

                        Intent intent = new Intent(MainActivity.this, Menu_Activity.class);
                        intent.putExtra("Fragment","List");
                        startActivity(intent);
                        finish();
                    } else {
                        id.setText("");
                        pwd.setText("");
                        Toast.makeText(MainActivity.this, "회원정보가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }




    }
}