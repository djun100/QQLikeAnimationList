package com.k.testandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ginkgoavenue.util.SetGifText;


public class MainActivity extends Activity {
    private static String TAG = "MainActivity";
    TextView tv;
    SetGifText sgt;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sgt = new SetGifText(this);
        inflater=getLayoutInflater();
        setContentView(R.layout.activity_main);
        initVeiw();
        Log.d(TAG, "onCreate start");
    }

    private void initVeiw() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(this.getFilesDir().getAbsolutePath().toString() + "/n");
        sgt.setSpannableText(tv, "[Bye][HI]Bye HI", 0);
        lv = (ListView) findViewById(R.id.lv);
        data = new ArrayList<String>();
        data.add("[Bye][HI]Bye HI");
        data.add("[Bye][HI]Bye HI");
        data.add("[Bye][HI]Bye HI");
        data.add("[Bye][HI]Bye HI");
        data.add("[Bye][HI]Bye HI");
        lv.setAdapter(new MyAdapter());

    }

    public static DisplayMetrics getScreenResolution(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
            TextView tv1=(TextView)convertView.findViewById(R.id.tv1);
            sgt = new SetGifText(MainActivity.this);
            sgt.setSpannableText(tv1,data.get(position), position);

            return convertView;
        }





    }
}
