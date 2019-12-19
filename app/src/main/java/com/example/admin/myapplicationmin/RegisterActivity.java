package com.example.admin.myapplicationmin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.LoginVO;

import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private LoginVO registerVO;
    private NetworkTask networkTask = new NetworkTask();
    private Button button;

    EditText user_id, user_pass, user_phone;
    EditText user_name, user_addr1, user_addr2, user_addr3,
            user_email, user_birth, user_pass2;
    CheckBox checkBox;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        user_id = (EditText)findViewById(R.id.user_id);
        user_pass = (EditText)findViewById(R.id.user_pass);
        user_phone = (EditText)findViewById(R.id.user_phone);
        user_name = (EditText)findViewById(R.id.user_name);
        user_addr1 = (EditText)findViewById(R.id.user_addr1);
        user_addr2 = (EditText)findViewById(R.id.user_addr2);
        user_addr3 = (EditText)findViewById(R.id.user_addr3);
        user_email = (EditText)findViewById(R.id.user_email);
        user_birth = (EditText)findViewById(R.id.user_birth);
        user_pass2 = (EditText)findViewById(R.id.user_pass2);
        button = (Button)findViewById(R.id.btn_button);







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = user_id.getText().toString().trim();
                String name = user_name.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String pass = user_pass.getText().toString().trim();
                String phone = user_phone.getText().toString().trim();
                String addr1 = user_addr1.getText().toString().trim();
                String addr2 = user_addr2.getText().toString().trim();
                String addr3 = user_addr3.getText().toString().trim();
                String birth = user_birth.getText().toString().trim();

                if (id.getBytes().length <=0 ) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if(!user_pass.getText().toString().equals(user_pass2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "입력하신 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }else if( email.getBytes().length <=0  ) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if( pass.getBytes().length <=0  ) {
                    Toast.makeText(getApplicationContext(), "비밀번호을 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if(phone.getBytes().length <=0 ) {
                    Toast.makeText(getApplicationContext(), "전봐번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if( addr1.getBytes().length <=0 ) {
                    Toast.makeText(getApplicationContext(), "우편번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if( addr2.getBytes().length <=0  ) {
                    Toast.makeText(getApplicationContext(), "기본주소를 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if( addr3.getBytes().length <=0) {
                    Toast.makeText(getApplicationContext(), "상세주소를 입력해주세요.", Toast.LENGTH_LONG).show();
                }else if( birth.getBytes().length <=0 ) {
                    Toast.makeText(getApplicationContext(), "생일을 입력해주세요.", Toast.LENGTH_LONG).show();
                }else  if( name.getBytes().length <=0 ) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_LONG).show();


                    }else {

                    //파라메터값
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Mapping","Register");
                    params.put("id", id);
                    params.put("pass", pass);
                    params.put("name", name);
                    params.put("birth", birth);
                    params.put("email", email);
                    params.put("phone", phone);
                    params.put("addr1", addr1);
                    params.put("addr2", addr2 );
                    params.put("addr3", addr3);



                    String msg = null;
                    try {
                        msg = networkTask.execute(params).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("흠...", msg);

                    //모델에 데이터 담기
                    Gson gson = new Gson();
                    registerVO = gson.fromJson(msg, LoginVO.class);
//                    Log.d("테스트이름...", registerVO.getId());

                    Toast.makeText(getApplicationContext(), "회원가입이 되었습니다.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    //  intent.putExtra("Reservation",registerVO);
                    startActivity(intent);
                    finish();


                }
            }
        });
    }
}
