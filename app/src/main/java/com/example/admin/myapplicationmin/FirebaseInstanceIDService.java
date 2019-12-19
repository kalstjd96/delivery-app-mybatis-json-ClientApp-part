package com.example.admin.myapplicationmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    String to;

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        //이는 fcm토큰이라는 개념으로
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "끄엑: " + token);

        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("token", token);

        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        sendRegistrationToServer(token); //이는 어플을 설치할때 한번 생성 @@@@



        //즉 생성한 토큰을 서버로 날려 저장할라고
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        //이는 서버로 토큰을 보낼 때 이쪽에서 활용하게 하려는 것

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        //pref.getString("token",token);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("token",token);
        ed.commit();

//        //OkHttpClient를 통해 웹서버로 토큰값을 날려준다.
//        OkHttpClient client = new OkHttpClient(); //@@ 28번줄이 만들어지자마자 http로 전송
//                                                //그럼 웹서버에서는 토큰을 받고 저장
//                                                //그 후 해당 토큰 메시지를 날려준다.
//        RequestBody body = new FormBody.Builder()
//                .add("Token", token)
//                .build();
//
//        //request  이부분은 상관 xxx
//        Request request = new Request.Builder()
//                .url("http://192.168.25.31:8080/doc/sendFCM")
//                //토큰을 저장하기위해 보낼 url
//                //.url("http://host/fcm/register.php")
//                .post(body)
//                .build();
//
//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
