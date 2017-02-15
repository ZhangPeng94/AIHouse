package app.zhangpeng.AIHouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.zhangpeng.greenhouse.R;

public class Note_Activity extends AppCompatActivity {
    private ListView listView;
    private ListView_Adapter listViewAdapter;
    private List<Map<String,String>> data;
    private Map<String,String> map;
    private Calendar c;
    private Note_SqlLite sqlLite;
    private Cursor cursor;
    private SQLiteDatabase SD;
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
        sqlLite=new Note_SqlLite(this,"Database.db",null,1);//使用数据库保存输入的数据
        data=new ArrayList<Map<String, String>>();
        listView=(ListView)findViewById(R.id.ListView);
        listViewAdapter=new ListView_Adapter(this,data);
        listView.setAdapter(listViewAdapter);
        listView.setDivider(null);
        SD=sqlLite.getWritableDatabase();
        cursor=SD.query("note",null,null,null,null,null,null,null);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            map=new HashMap<String, String>();
            map.put("money",cursor.getString(1));
            map.put("date",cursor.getString(2));
            map.put("time",cursor.getString(3));
            data.add(map);
            cursor.moveToNext();
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog1=new AlertDialog.Builder(Note_Activity.this);
                dialog1.setMessage("确定删除吗？");
                dialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        SD=sqlLite.getWritableDatabase();
                        cursor=SD.query("note",null,null,null,null,null,null,null);
                        cursor.moveToPosition(position);
                        sqlLite.getWritableDatabase().execSQL("DELETE FROM note where time=?",new String[]{cursor.getString(3)});
                        listViewAdapter.notifyDataSetChanged();
                    }
                }).show();

                return false;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.clear) {
                    AlertDialog.Builder clear=new AlertDialog.Builder(Note_Activity.this);
                    clear.setMessage("确定全部清除吗？");
                    clear.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            data.clear();
                            sqlLite.getWritableDatabase().execSQL("DELETE FROM note");
                            listViewAdapter.notifyDataSetChanged();
                        }
                    }).show();
                }
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText=new EditText(Note_Activity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder dialog=new AlertDialog.Builder(Note_Activity.this);
                dialog.setTitle("请输入收入金额");
                dialog.setView(editText);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map=new HashMap<String, String>();
                        c= Calendar.getInstance();
                        String money;
                        String date;
                        String time;
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        money=editText.getText().toString()+"¥";
                        date=Integer.toString(year)+"-"+Integer
                                .toString(month)+"-"+Integer.toString(day);
                        time=Integer.toString(c.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(c.get(Calendar.MINUTE))+":"+Integer.toString(c.get(Calendar.SECOND));
                        map.put("money",money);
                        map.put("date",date);
                        map.put("time",time);
                        data.add(map);
                        sqlLite.getWritableDatabase().execSQL("insert into note(content,date,time) values(?,?,?)",new String[]{money,date,time});
                        listViewAdapter.notifyDataSetChanged();
                    }
                }).show();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu_item, menu);
        return true;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (sqlLite!=null){
            sqlLite.close();
        }
    }

}
