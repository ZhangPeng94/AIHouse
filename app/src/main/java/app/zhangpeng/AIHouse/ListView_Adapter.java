package app.zhangpeng.AIHouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import app.zhangpeng.greenhouse.R;

/**
 * Created by zhangpeng on 2017-02-13.
 */

public class ListView_Adapter extends BaseAdapter {
    private Context context=null;
    private LayoutInflater mInflater;
    private List<Map<String,String>> data;
    public ListView_Adapter(Context context, List<Map<String,String>> data) {
        this.context = context;
        this.data = data;
        mInflater=LayoutInflater.from(context);
    }
    private class Holder{
        public TextView tx1;
        public TextView tx2;
        public TextView tx3;
        public TextView getTx1(){
            return tx1;
        }
        public void setTx1(TextView item_tex) {
            this.tx1 = item_tex;
        }
        public TextView getTx2(){
            return tx2;
        }
        public void setTx2(TextView item_tex) {
            this.tx2 = item_tex;
        }
        public TextView getTx3(){
            return tx3;
        }
        public void setTx3(TextView item_tex) {
            this.tx3 = item_tex;
        }
    }
    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ListView_Adapter.Holder holder;
        if(view==null){
            view=mInflater.inflate(R.layout.listview_item,null);
            holder=new ListView_Adapter.Holder();
            holder.tx1=(TextView)view.findViewById(R.id.tx1);
            holder.tx2=(TextView)view.findViewById(R.id.tx2);
            holder.tx3=(TextView)view.findViewById(R.id.tx3);
            view.setTag(holder);
        }else{
            holder=(ListView_Adapter.Holder) view.getTag();
        }
        holder.tx1.setText(data.get(position).get("money"));
        holder.tx2.setText(data.get(position).get("date"));
        holder.tx3.setText(data.get(position).get("time"));
        return view;
    }
}
