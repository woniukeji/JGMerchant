package com.woniukeji.jgmerchant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.MapView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {

    private MapView mMapView;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        locationOption.setInterval(2000);
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setNeedAddress(true);
        locationOption.setOnceLocation(false);
        locationOption.setMockEnable(false);
        locationClient.setLocationListener(this);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    private static final String TAG = "MainActivity";
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                Log.i(TAG, aMapLocation.getLocationType() + "");//获取当前定位结果来源，如网络定位结果，详见定位类型表
                Log.i(TAG, aMapLocation.getLatitude() + "");//获取纬度
                Log.i(TAG, aMapLocation.getLongitude() + "");//获取经度
                Log.i(TAG, aMapLocation.getAccuracy() + "");//获取精度信息
                Log.i(TAG, aMapLocation.getAddress());
                Log.i(TAG, aMapLocation.getCountry());
                Log.i(TAG, aMapLocation.getProvince());
                Log.i(TAG, aMapLocation.getCity());
                Log.i(TAG, aMapLocation.getCity());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                Log.i(TAG, df.format(date) + "");//定位时间
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
