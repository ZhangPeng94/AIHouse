package app.zhangpeng.AIHouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by zhangpeng on 2017-02-13.
 */

public class Note_SqlLite extends SQLiteOpenHelper {
    private Context context;
    public static final String Create_Database="create table note (id integer primary key autoincrement,content text,date text,time text)";
    public Note_SqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);

        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Create_Database);//第一次使用时自动创建
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
