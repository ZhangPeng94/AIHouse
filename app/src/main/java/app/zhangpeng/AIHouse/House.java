package app.zhangpeng.AIHouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.interfaces.iData.ILineData;

import java.util.ArrayList;

import app.zhangpeng.greenhouse.R;

public class House extends AppCompatActivity {//需要返回数据给上层activity
    private GridView gridView;
    private GridView_Adapter adapter;
    private LineData lineData=new LineData();
    private ArrayList<ILineData> DataList=new ArrayList<>();
    private ArrayList<PointF> PointFArrayList=new ArrayList<>();
    public SharedPreferences HouseX_Sp;
    public SharedPreferences.Editor HouseX_Ed;
    private int variety=-1;
    private String house;
    private int[] icon={R.drawable.hongmaoji,R.drawable.pen,R.drawable.calendar,
                        R.drawable.feed,R.drawable.syringe,R.drawable.rmb};
    private String[] name={"选择种类","数量编辑","日期设置","饲料","预防","记账"};
    final String[] strings={"红毛肉鸡","乌骨鸡","杂交鸡"};
    protected float[][] points = new float[][]{{0,33},{1, 30}, {2, 35}, {3, 38}, {4, 37}, {5, 34},
            {6, 36}, {7,32}, {8, 37}, {9, 35},
            {10, 31},{11, 33}, {12, 32}, {13,37}, {14, 38}, {15,
            39}, {16, 32}, {17, 34},
            {18, 37}, {19, 34}, {20, 31},{22,34},{23,35}};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_text=(TextView)findViewById(R.id.toolbar_text);
        Intent intent=this.getIntent();
        house=intent.getStringExtra("name");
        toolbar.setTitle("温室"+house+"号");
        DateDialog date=new DateDialog();
        if (house.equals("1")){
            HouseX_Ed=getSharedPreferences("house1",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house1",MODE_PRIVATE);
        }else if (house.equals("2")){
            HouseX_Ed=getSharedPreferences("house2",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house2",MODE_PRIVATE);
        }else if (house.equals("3")){
            HouseX_Ed=getSharedPreferences("house3",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house3",MODE_PRIVATE);
        }else if (house.equals("4")){
            HouseX_Ed=getSharedPreferences("house4",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house4",MODE_PRIVATE);
        }else if (house.equals("5")){
            HouseX_Ed=getSharedPreferences("house5",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house5",MODE_PRIVATE);
        }else if (house.equals("6")){
            HouseX_Ed=getSharedPreferences("house6",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house6",MODE_PRIVATE);
        }else if (house.equals("7")){
            HouseX_Ed=getSharedPreferences("house7",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house7",MODE_PRIVATE);
        }else if (house.equals("8")){
            HouseX_Ed=getSharedPreferences("house8",MODE_PRIVATE).edit();
            HouseX_Sp=getSharedPreferences("house8",MODE_PRIVATE);
        }
        date.HouseX_Ed=HouseX_Ed;
        date.HouseX_Sp=HouseX_Sp;

        toolbar_text.setText("装入日期\n"+HouseX_Sp.getString("date","2018-8-08"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(House.this,MainActivity.class);
                startActivity(intent);
            }
        });
        initData();
        LineChart lineChart=(LineChart)findViewById(R.id.lineChart);
        lineChart.setDataList(DataList);
        gridView=(GridView)findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        variety=-1;
                        final AlertDialog.Builder vary=new AlertDialog.Builder(House.this);
                        vary.setSingleChoiceItems(strings, HouseX_Sp.getInt("variety",0),
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        variety=which;
                                        icon[0]=R.drawable.hongmaoji;
                                        break;
                                    case 1:
                                        variety=which;
                                        icon[0]=R.drawable.wuguji;
                                        break;
                                    case 2:
                                        variety=which;
                                        icon[0]=R.drawable.zajiaoji;
                                        break;
                                }
                            }
                        });
                        vary.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (variety!=-1){
                                    HouseX_Ed.putInt("variety",variety);
                                    HouseX_Ed.apply();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        vary.show();
                        Toast.makeText(House.this,"种类选择",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        final EditText editText=new EditText(House.this);
                        AlertDialog.Builder num=new AlertDialog.Builder(House.this);
                        num.setTitle("请输入装入数量：");
                        num.setView(editText);
                        editText.setText(HouseX_Sp.getString("number","0"));
                        num.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HouseX_Ed.putString("number",editText.getText().toString());
                                HouseX_Ed.apply();
                            }
                        }).show();

                        Toast.makeText(House.this,"数量编辑",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        DateDialog dateDialog=new DateDialog();
                        dateDialog.show(getFragmentManager(),"datePicker");
                        Toast.makeText(House.this,"日期设置",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        EditText editText1=new EditText(House.this);
                        AlertDialog.Builder feednum=new AlertDialog.Builder(House.this);
                        feednum.setTitle("请输入饲料数量").setView(editText1)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                        Toast.makeText(House.this,"饲料",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(House.this,"预防",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(House.this,"记账",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(House.this,"Nothing",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        if (HouseX_Sp.getInt("variety",0)==0){
            icon[0]=R.drawable.hongmaoji;
        }else if (HouseX_Sp.getInt("variety",0)==1){
            icon[0]=R.drawable.wuguji;
        }else if (HouseX_Sp.getInt("variety",0)==2){
            icon[0]=R.drawable.zajiaoji;
        }
        adapter=new GridView_Adapter(this,name,icon);
        gridView.setAdapter(adapter);
    }
    private void initData(){
        for (int i = 0; i < 23; i++) {
            PointFArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        lineData.setValue(PointFArrayList);
        lineData.setColor(Color.RED);
        DataList.add(lineData);

    }
    @Override
    public void onBackPressed(){
        finish();
        Intent intent=new Intent(House.this,MainActivity.class);
        startActivity(intent);
    }
}
