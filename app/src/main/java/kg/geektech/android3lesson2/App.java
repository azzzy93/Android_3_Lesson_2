package kg.geektech.android3lesson2;

import android.app.Application;

import kg.geektech.android3lesson2.data.remote.HerokuApi;
import kg.geektech.android3lesson2.data.remote.RetrofitClient;

public class App extends Application {
    public static HerokuApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();
    }
}
