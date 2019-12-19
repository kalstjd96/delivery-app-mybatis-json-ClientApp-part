package com.example.admin.myapplicationmin.NONO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplicationmin.Model.ListVO;
import com.example.admin.myapplicationmin.Network.NetworkTask;
import com.example.admin.myapplicationmin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ListViewActivity extends AppCompatActivity {
    private EditText product_num;
    private Button search_btn;
    private ListView resultView;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private ArrayList<String> arraylist;
    private Button button;
    private Intent intent;
    private  Button logout;
    private  TextView label;

    private ListVO listVO;

    private  List<ListVO> list3 = new ArrayList<>();

    String User_MNO;


    /*****************************************삭제 예정*************************************/
    SwipeRefreshLayout swipeRefreshLayout; //3

    private NetworkTask networkTask = new NetworkTask();
    private int position2 =0;
    private int[] list5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        SharedPreferences auto = getSharedPreferences("auto", MODE_PRIVATE);
        User_MNO = auto.getString("inputMNO","회원번호오류");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        listView = (ListView) findViewById(R.id.listview1);













        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.

        settingList();


        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        // 이부분은 SearchAdapter과 연결하는 코드임
        //즉 list에 있는 항목들을 대상으로 검색결과를 툴력하기 위한 것








        ListAdapter myadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                list.get(position);
                Toast.makeText(getApplicationContext(),Integer.toString(position2 - position),
                        Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(ListViewActivity.this, DetailActivity.class);
                intent3.putExtra("str", Integer.toString(position2 - position)); //아이디값을 sub로 보내는 코드
                startActivity(intent3);//액티비티 띄우기
                finish();




            }
        });
    }






    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){




        networkTask = new NetworkTask();
        Map<String, String> params = new HashMap<String, String>();

        params.put("Mapping", "List");
        params.put("MNO", User_MNO);


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

        TypeToken<List<ListVO>> typeToken = new TypeToken<List<ListVO>>() {};
        List<ListVO> list2 = gson.fromJson(msg, typeToken.getType());
        list3 = gson.fromJson(msg, typeToken.getType());


        for (ListVO TestVO : list2) {
            //list2의 값을 TestVO에 넣는다


                Log.i("테스트주소1...", TestVO.getId());
                Log.i("테스트주소2...", TestVO.getName());
                Log.i("테스트주소3...", TestVO.getNumbers());
                Log.i("테스트주소4...", TestVO.getPhone());
                Log.i("테스트주소5...", TestVO.getAddress());


               list.add("ID: "+TestVO.getIdx()+"  Name: "+TestVO.getName());

              position2 += 1;

        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                list.clear();
                networkTask = new NetworkTask();
                Map<String, String> params = new HashMap<String, String>();

                params.put("Mapping", "List");
                params.put("MNO", User_MNO);


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

                TypeToken<List<ListVO>> typeToken = new TypeToken<List<ListVO>>() {};
                List<ListVO> list2 = gson.fromJson(msg, typeToken.getType());
                list3 = gson.fromJson(msg, typeToken.getType());

                for (ListVO TestVO : list2) {
                    //list2의 값을 TestVO에 넣는다


                    Log.i("테스트주소1...", TestVO.getId());
                    Log.i("테스트주소2...", TestVO.getName());
                    Log.i("테스트주소3...", TestVO.getNumbers());
                    Log.i("테스트주소4...", TestVO.getPhone());
                    Log.i("테스트주소5...", TestVO.getAddress());


                    list.add("ID: "+TestVO.getIdx()+"  Name: "+TestVO.getName());
                    position2 += position2+1;
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

}
