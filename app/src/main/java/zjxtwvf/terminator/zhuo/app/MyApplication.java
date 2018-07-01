package zjxtwvf.terminator.zhuo.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class MyApplication extends Application {
	private static Context context;
	private static int mainThreadId;

	public static MyApplication getContext() {
		return (MyApplication)context;
	}

	public static Handler getHandler() {
		return handler;
	}

	private static Handler handler;



	@Override
	public void onCreate() {
		context = getApplicationContext();
		handler = new Handler();
		mainThreadId =  android.os.Process.myTid();
		super.onCreate();
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}

	public MyApplication getInstance()
	{
		return this;
	}
}
