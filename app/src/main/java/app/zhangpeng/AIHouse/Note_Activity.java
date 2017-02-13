package app.zhangpeng.AIHouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.zhangpeng.greenhouse.R;

public class Note_Activity extends AppCompatActivity {
    private ListView listView;
    private ListView_Adapter listViewAdapter;
    private List<Map<String,String>> data;
    private Map<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        map=new HashMap<String, String>();
        data=new ArrayList<Map<String, String>>();

        listView=(ListView)findViewById(R.id.ListView);
        listViewAdapter=new ListView_Adapter(this,data);
        listView.setAdapter(listViewAdapter);
        listView.setDivider(null);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog1=new AlertDialog.Builder(Note_Activity.this);
                dialog1.setMessage("确定删除吗？");
                dialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }).show();

                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=new EditText(Note_Activity.this);
                AlertDialog.Builder dialog=new AlertDialog.Builder(Note_Activity.this);
                dialog.setTitle("请输入收入金额");
                dialog.setView(editText);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map.put("money","1500");
                        map.put("date","2018-8-8");
                        data.add(map);
                        listViewAdapter.notifyDataSetChanged();
                    }
                }).show();

            }
        });
    }

}
