package com.example.admin.myapplicationmin.DeliveryList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplicationmin.Model.DeliveryVO;
import com.example.admin.myapplicationmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class deliveryListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<DeliveryVO> listViewItemList = new ArrayList<DeliveryVO>() ;

    // ListViewAdapter의 생성자
    public deliveryListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        LinearLayout line_layout = (LinearLayout) convertView.findViewById(R.id.line_layout);
        TextView product_nameTextView = (TextView) convertView.findViewById(R.id.txt_product_Name) ;
        TextView se_nameTextView = (TextView) convertView.findViewById(R.id.txt_SE_Name) ;
        TextView numTextView = (TextView) convertView.findViewById(R.id.txt_product_Num) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.text_delivery_deta) ;
        TextView typeTextView = (TextView) convertView.findViewById(R.id.txt_delivery_type) ;
        TextView dateTextView2 = (TextView)convertView.findViewById(R.id.text_delivery_deta2);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DeliveryVO DeliveryVO = listViewItemList.get(position);

        String req = null;
        try {
            req = getDateDay(DeliveryVO.getSe_req(),"yy/MM/dd");
        } catch (Exception e) {
            e.printStackTrace();
        }




        // 아이템 내 각 위젯에 데이터 반영

        product_nameTextView.setText(DeliveryVO.getProduct_name());
        se_nameTextView.setText(DeliveryVO.getSe_name());
        numTextView.setText(DeliveryVO.getWaybill_number());
        dateTextView.setText(DeliveryVO.getProcess_date());
        dateTextView2.setText(req);

        switch (DeliveryVO.getDelivery_type()){
            case "0":
                typeTextView.setText("택배예약");
                line_layout.setBackgroundResource(R.color.material_pink_400);
                typeTextView.setTextColor(Color.parseColor("#EC407A"));
                break;
            case "1":
                typeTextView.setText("배송전");
                line_layout.setBackgroundResource(R.color.material_deep_orange_100);
                typeTextView.setTextColor(Color.parseColor("#FFCCBC"));
                break;
            case "2":
                typeTextView.setText("배송중");
                line_layout.setBackgroundResource(R.color.material_green_400);
                typeTextView.setTextColor(Color.parseColor("#66BB6A"));
                break;
            case "3":
                typeTextView.setText("배송완료");
                line_layout.setBackgroundResource(R.color.material_light_blue_accent_700);
                typeTextView.setTextColor(Color.parseColor("#0091EA"));
                break;
             default:
                 typeTextView.setText("오류!!");
                 line_layout.setBackgroundResource(R.color.material_light_black);
                 typeTextView.setTextColor(Color.parseColor("#000000"));
                 break;
        }





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
    public void addItem(String p_name, String se_name, String num, String date, String type, String req) {


        DeliveryVO item = new DeliveryVO();

        item.setSe_name(se_name);
        item.setProduct_name(p_name);
        item.setWaybill_number(num);
        item.setProcess_date(date);
        item.setDelivery_type(type);
        item.setSe_req(req);

        listViewItemList.add(item);
    }

    public String getDateDay(String date, String dateType) throws Exception {


        String day = "" ;

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;



        switch(dayNum){
            case 1:
                day = "일요일";
                break ;
            case 2:
                day = "월요일";
                break ;
            case 3:
                day = "화요일";
                break ;
            case 4:
                day = "수요일";
                break ;
            case 5:
                day = "목요일";
                break ;
            case 6:
                day = "금요일";
                break ;
            case 7:
                day = "토요일";
                break ;

        }



        return day ;
    }
}
