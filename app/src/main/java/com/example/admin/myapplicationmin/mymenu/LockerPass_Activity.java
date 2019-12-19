package com.example.admin.myapplicationmin.mymenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplicationmin.R;

public class LockerPass_Activity extends AppCompatActivity {

    EditText edt_pass;
    Button btn_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_pass);
        setTitle("비밀번호확인");

        edt_pass = (EditText)findViewById(R.id.edt_pass);
        btn_pass = (Button)findViewById(R.id.btn_pass);

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);

        final String User_PWD = auto.getString("inputPwd","비밀번호오류");

        Intent intent1 = getIntent();
        final String SNO = intent1.getExtras().getString("SNO");

        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = edt_pass.getText().toString().trim();
                if(User_PWD.equals(pass)){
                    Intent intent = new Intent(LockerPass_Activity.this, LockerNFC_Activity.class);
                    intent.putExtra("SNO",SNO);
                    startActivity(intent);
                    finish();
                } else {
                    edt_pass.setText("");
                    Toast.makeText(LockerPass_Activity.this, "비밀번호가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
