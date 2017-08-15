package com.facebook.administrator.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * 服务共享
 * Created by Administrator on 2017/8/15/015.
 */

public class IRemoteService extends Service {
    /**
     * 当客户端绑定到该服务时，调用
     * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int number1, int number2) throws RemoteException {
            Log.d("TAG","收到了远程的请求，输入的参数"+number1+"和"+number2);
//            Toast.makeText(IRemoteService.this,"收到了远程的请求，输入的参数"+number1+"和"+number2,Toast.LENGTH_SHORT).show();
            return number1+number2;
        }
    };

}
