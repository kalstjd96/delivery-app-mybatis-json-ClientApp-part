package com.example.admin.myapplicationmin.mymenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.R;

import java.util.ArrayList;

public class myListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<DeliveryVO> listViewItemList = new ArrayList<DeliveryVO>() ;

    // ListViewAdapter의 생성자
    public myListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mylistview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView nameTextView = (TextView) convertView.findViewById(R.id.text_Name) ;
        TextView numTextView = (TextView) convertView.findViewById(R.id.text_Num) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.text_Date) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DeliveryVO DeliveryVO = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        nameTextView.setText(DeliveryVO.getProduct_name());
        numTextView.setText(DeliveryVO.getWaybill_number());
        dateTextView.setText(DeliveryVO.getProcess_date());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String num, String date) {
        DeliveryVO item = new DeliveryVO();

        item.setProduct_name(name);
        item.setWaybill_number(num);
        item.setProcess_date(date);

        listViewItemList.add(item);
    }
}
