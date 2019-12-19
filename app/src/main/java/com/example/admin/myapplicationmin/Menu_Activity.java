package com.example.admin.myapplicationmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.myapplicationmin.DeliveryList.DeliveryList_Activity;
import com.example.admin.myapplicationmin.DeliveryList.Search_Activity;
import com.example.admin.myapplicationmin.Reservation.Refund_Activity;
import com.example.admin.myapplicationmin.Reservation.Reservation_Activity;
import com.example.admin.myapplicationmin.mymenu.MyMenu_Activity;

public class Menu_Activity extends AppCompatActivity {


    private Button btn_list,btn_my,btn_res,btn_ref,btn_Search,btn_test;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 3개의 메뉴에 들어갈 Fragment들
    private DeliveryList_Activity menu1Fragment = new DeliveryList_Activity();
    private MyMenu_Activity menu2Fragment = new MyMenu_Activity();
    private Reservation_Activity menu3Fragment = new Reservation_Activity();
    private Refund_Activity menu4Fragment = new Refund_Activity();
    private Search_Activity menu5Fragment = new Search_Activity();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("");



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();



        Intent intent1 = getIntent();
        String Fragment = intent1.getExtras().getString("Fragment");

            switch (Fragment){
                case "List":
                    transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                    break;
                case "Menu":
                    transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                    break;
                case "Reservation":
                    transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                    break;
                case "Refund":
                    transaction.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                    break;
                case "Search":
                    transaction.replace(R.id.frame_layout, menu5Fragment).commitAllowingStateLoss();
                    break;

                default:
                    break;
            }

        bottomNavigationView.performClick();


        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        transaction.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu5: {
                        transaction.replace(R.id.frame_layout, menu5Fragment).commitAllowingStateLoss();
                        break;
                    }


                }





                return true;
            }
        });




//        //마이메뉴
//        btn_my.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), MyMenu_Activity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        //배송목록
//        btn_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), DeliveryList_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        //택배예약
//        btn_res.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Reservation_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        //환불요청
//        btn_ref.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Refund_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        //내사물함 확인
//        btn_Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Search_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        //테스트페이지
//        btn_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), test.class);
//                startActivity(intent);
//            }
//        });








    }
}
