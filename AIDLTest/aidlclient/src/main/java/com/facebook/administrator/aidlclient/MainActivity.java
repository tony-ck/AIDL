package com.facebook.administrator.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.administrator.aidltest.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IMyAidlInterface imif;
    private EditText et_num1,et_num2,et_num3;
    private ServiceConnection conn = new ServiceConnection() {
        //绑定服务
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到其他进程的服务
             imif=IMyAidlInterface.Stub.asInterface(service);
        }
        //断开服务
        @Override
        public void onServiceDisconnected(ComponentName name) {
            imif=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindservice();
    }

    private void initView() {
         et_num1 = (EditText) findViewById(R.id.et_num1);
         et_num2 = (EditText) findViewById(R.id.et_num2);
         et_num3 = (EditText) findViewById(R.id.et_result);
        Button btn_AIDl  = (Button) findViewById(R.id.bt_addresult);
        btn_AIDl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_addresult){
            //非空判断
            if(TextUtils.isEmpty(et_num1.getText().toString())){
                return;
            }
            if(TextUtils.isEmpty(et_num2.getText().toString())){
                return;
            }
            try {
                int res = imif.add(Integer.parseInt(et_num1.getText().toString()),Integer.parseInt(et_num2.getText().toString()));
                et_num3.setText(res+"");
            } catch (RemoteException e) {
                e.printStackTrace();
                et_num3.setText("计算错误");
            }
        }
    }
    //ctrl+alt+M
    private void bindservice() {
        //获取到服务端,显式调用
        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName("com.facebook.administrator.aidltest",
                        "com.facebook.administrator.aidltest.IRemoteService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
