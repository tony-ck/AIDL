#AIDL 是跨进程通信的实现的一种。

》》 远程服务端,当客户端绑定该服务的时候，获得一个IBinder对象。

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

***记得注册该服务***

    <service android:name=".IRemoteService"
            android:exported="true"
            android:process=":remote"
            />
         
》》 客户端
  onCreate方法中绑定服务：
  //获取到服务端,显式调用
  
    Intent intent = new Intent();
    intent.setComponent(
                new ComponentName("com.facebook.administrator.aidltest",
              "com.facebook.administrator.aidltest.IRemoteService"));
    bindService(intent,conn, Context.BIND_AUTO_CREATE);
 
             
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
    
     //解除绑定
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
