package com.htw.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SocketFragment extends Fragment implements UseSocket.SocketListener {
    private static final String TAG = "SocketFragment";
    private static final String KEY = "SocketFragment_key";

    private TextView mShowView = null;
    private EditText mEditView = null;
    private Button mSendButton = null;

    private UseSocket mUseSocket = null;
    private String mMsg = null;

    @SuppressLint("ValidFragment")
    private SocketFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.use_socket, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUseSocket == null) {
            mUseSocket = new UseSocket(this);
        }
        mUseSocket.connect();
    }

    private void initView(View view) {
        mShowView = (TextView) view.findViewById(R.id.use_socket_show_content);
        mEditView = (EditText) view.findViewById(R.id.use_socket_msg);
        mSendButton = (Button) view.findViewById(R.id.use_socket_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mEditView.getText().toString();
                Log.e("huangtanwen", "send msg = " + msg);
                mUseSocket.send(msg);
                mEditView.setText("");
                mMsg += "\n" + msg;
                mShowView.setText(mMsg);
            }
        });
    }

    public static SocketFragment newInstance(String value) {
        SocketFragment fragment = new SocketFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String value = getArguments().get(KEY) + "";
        Log.v(TAG, "SocketFragment onAttach value = " + value);
    }

    @Override
    public void onConnectSuccess(String msg) {
        Log.e("huangtanwen", "onConnectSuccess");
        mMsg += "\n" + msg;
        mShowView.setText(msg);
    }

    @Override
    public void onSendSuccess(String msg) {
        Log.e("huangtanwen", "onSendSuccess : " + msg);
        mMsg += "\n" + msg;
        mShowView.setText(msg);
    }

    @Override
    public void onClosed() {
        Log.e("huangtanwen", "onClosed");
    }
}
