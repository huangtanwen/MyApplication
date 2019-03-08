package com.htw.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HtmlFragment extends Fragment {
    private static final String KEY = "HtmlFragment_key";
    private UseHtml mUseHtml = null;

    @SuppressLint("ValidFragment")
    private HtmlFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.use_html, container, false);
        mUseHtml = new UseHtml(view, getActivity());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String value = getArguments().getString(KEY);
    }

    public static HtmlFragment newInstance(String value) {
        HtmlFragment htmlFragment = new HtmlFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, value);
        htmlFragment.setArguments(bundle);
        return htmlFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
