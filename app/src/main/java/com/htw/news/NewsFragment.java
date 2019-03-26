package com.htw.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.htw.main.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class NewsFragment extends Fragment {
    private static final String KEY = "HtmlFragment_key";
    private static final String NEWS_URL = "http://v.juhe.cn/toutiao/index?type=top&key=a1a755458cc22f129942b34904feb820";

    private ListView mListView = null;

    @SuppressLint("ValidFragment")
    private NewsFragment() {

    }

    public static NewsFragment newInstance(String value) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String value = getArguments().getString(KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.news_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("huangtanwen", "onResume");
        new Thread() {
            @Override
            public void run() {
                String news = null;
                try {
                    news = HtmlService.getHtml(NEWS_URL);
                    Log.e("huangtanwen", "news = " + news);
                    JSONObject jsonObject = new JSONObject(news);
                    String reason = jsonObject.getString("reason");
                    Log.e("huangtanwen", "reason = " + reason);
                    String result2 = jsonObject.getString("result");
                    Log.e("huangtanwen", "result2 = " + result2);
                    JSONObject jsonObject2 = new JSONObject(result2);
                    String stat = jsonObject2.getString("stat");
                    Log.e("huangtanwen", "stat = " + stat);
                    String data = jsonObject2.getString("data");
                    JSONArray jsonArray2 = new JSONArray(data);
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        //获取每一个JsonObject对象
                        JSONObject myjObject = jsonArray2.getJSONObject(i);
                        if (myjObject != null) {
                            Log.e("huangtanwen","title = " + myjObject.get("title"));
                        }
                    }
                    /*for(int i = 0; i < jsonArray.length(); i++) {
                        Log.e("huangtanwen", "jsonArray(" +i + ") = " + jsonArray.get(i));
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("huangtanwen", "getHtml failed!", e);
                }
            }
        }.start();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.item_news, null);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        private class ViewHolder {

        }
    }

}
