package app.zhangpeng.AIHouse;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import app.zhangpeng.greenhouse.R;

/**
 * Created by zhangpeng on 2017-02-11.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView textView;
    public static SharedPreferences HouseX_Sp;
    public static SharedPreferences.Editor HouseX_Ed;
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       // Use the current date as the default date in the picker
       final Calendar c = Calendar.getInstance();
       int year = c.get(Calendar.YEAR);
       int month = c.get(Calendar.MONTH);
       int day = c.get(Calendar.DAY_OF_MONTH);
       // Create a new instance of DatePickerDialog and return it
       return new DatePickerDialog(getActivity(), this, year, month, day);
   }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        textView=(TextView)getActivity().findViewById(R.id.toolbar_text);
        textView.setText("装入日期\n"+Integer.toString(year)+"-"+Integer
                .toString(month+1)+"-"+Integer.toString(day));
        HouseX_Ed.putString("date",Integer.toString(year)+"-"+Integer
                .toString(month+1)+"-"+Integer.toString(day));
        HouseX_Ed.apply();
        // Do something with the date chosen by the user
        MainActivity mainActivity=new MainActivity();
        mainActivity.mListAdapter.notifyDataSetChanged();
    }
}
