package app.zhangpeng.AIHouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.zhangpeng.greenhouse.R;

/**
 * Created by zhangpeng on 2017-01-26.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> implements View.OnClickListener{
    private List<String> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private SharedPreferences HouseX;
    private static Context context;
    private SimpleDateFormat format;
    private Calendar day,today;
    ParsePosition position ;
    public ListAdapter(Context context, List<String> mDatas){
        this.mDatas=mDatas;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {//将数据显示出来
        itemViewHolder.mTextview.setText(mDatas.get(i));

        switch (i){
            case 0:
                HouseX=context.getSharedPreferences("house1",context.MODE_PRIVATE);
                break;
            case 1:
                HouseX=context.getSharedPreferences("house2",context.MODE_PRIVATE);
                break;
            case 2:
                HouseX=context.getSharedPreferences("house3",context.MODE_PRIVATE);
                break;
            case 3:
                HouseX=context.getSharedPreferences("house4",context.MODE_PRIVATE);
                break;
            case 4:
                HouseX=context.getSharedPreferences("house5",context.MODE_PRIVATE);
                break;
            case 5:
                HouseX=context.getSharedPreferences("house6",context.MODE_PRIVATE);
                break;
            case 6:
                HouseX=context.getSharedPreferences("house7",context.MODE_PRIVATE);
                break;
            case 7:
                HouseX=context.getSharedPreferences("house8",context.MODE_PRIVATE);
                break;
        }
        if (HouseX.getInt("variety",0)==0){
            itemViewHolder.imageView.setImageResource(R.drawable.hongmaoji);
        }else if (HouseX.getInt("variety",0)==1) {
            itemViewHolder.imageView.setImageResource(R.drawable.wuguji);
        }else if (HouseX.getInt("variety",0)==2) {
            itemViewHolder.imageView.setImageResource(R.drawable.zajiaoji);
        }
        position= new ParsePosition(0);
        format=new SimpleDateFormat("yyyy-MM-dd");
        day=Calendar.getInstance();
        today=Calendar.getInstance();
        day.setTime(format.parse(HouseX.getString("date","2017-1-1"),position));
        today.setTime(new Date());
        itemViewHolder.days.setText(Integer.toString(day_number(today,day))+"天");
        itemViewHolder.itemView.setTag(mDatas.get(i));
    }
    @Override
    public void onClick(View v){
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener=listener;
    }
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {//创建viweholder
        View view=mInflater.inflate(R.layout.recycle_item, viewGroup, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }
    public void add(int position, String value) {
        if(position > mDatas.size()) {
            position = mDatas.size();
        }
        if(position < 0) {
            position = 0;
        }
        mDatas.add(position, value);
        /**
         * 使用notifyItemInserted/notifyItemRemoved会有动画效果
         * 而使用notifyDataSetChanged()则没有
         */
        notifyItemInserted(position);
    }
    public String remove(int position) {
        if(position > mDatas.size()-1) {
            return null;
        }
        String value = mDatas.remove(position);
        notifyItemRemoved(position);
        return value;
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {//创建自己的viewholder类
        TextView mTextview;
        ImageView imageView;
        TextView days;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextview=(TextView) itemView.findViewById(R.id.mTextview);
            imageView=(ImageView) itemView.findViewById(R.id.imageView6);
            days=(TextView)itemView.findViewById(R.id.textView2);
        }
    }
    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,String data);
    }
    public Context getContex(Context context){
        this.context=context;
        return this.context;
    }
    public int day_number(Calendar after_day, Calendar before_day){
        int days;
        if (after_day.get(Calendar.YEAR)==before_day.get(Calendar.YEAR))
        {
            days=after_day.get(Calendar.DAY_OF_YEAR)-before_day.get(Calendar.DAY_OF_YEAR);
        }
        else
        {
            days=before_day.getMaximum(Calendar.DAY_OF_YEAR)-before_day.get(Calendar.DAY_OF_YEAR)+after_day.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }
}
