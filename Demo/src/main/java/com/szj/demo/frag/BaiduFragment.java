package com.szj.demo.frag;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNRoutePlanNode;

import com.szj.demo.BaiduPanoActivity;
import com.szj.demo.R;
import com.szj.demo.overlayutil.WalkingRouteOverlay;
import com.szj.demo.utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 1 百度下载开发包
 * 2 将包放入lib 然后 修改gadle
 * 3 得到簽名 es 为android build  as 为C:\Users\Administrator\.android 然后keytool -list -v -keystore debug.keystore 也可以使用自的密钥库
 * 4 的到key  	加入到配置文件中   android:name="com.baidu.lbsapi.API_KEY"
 * 5 定位需要在配置文件中加入服务 android:name="com.baidu.location.f"
 * 6 在application中使用   SDKInitializer.initialize(getApplicationContext());
 */

public class BaiduFragment extends Fragment implements OnGetRoutePlanResultListener, BaiduMap.OnMapClickListener {

    private static final String TAG = BaiduFragment.class.getSimpleName();

    @InjectView(R.id.fg_baidu_plan)
    TextView fgBaiduPlan;
    @InjectView(R.id.fg_baidu_nav)
    TextView fgBaiduNav;
    @InjectView(R.id.fg_baidu_pano)
    TextView fgBaiduPano;
    //地图相关
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    //定位相关
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    // 是否首次定位
    private boolean isFirstLoc = true;
    //路径规划
    private RoutePlanSearch mSearch;

    private LatLng startLatLng;
    private LatLng stopLatLng;

    public BaiduFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baidu, container, false);
        ButterKnife.inject(this, view);
        initViews(view);

        return view;
    }

    private void initViews(View view) {

        // 获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.fg_baidu_mv);
        mBaiduMap = mMapView.getMap();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(view.getContext().getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //初始化定位返回数据
        initLocation();
        //开始定位
        mLocationClient.start();
        //地图点击
        mBaiduMap.setOnMapClickListener(this);

        // 获取路径规划的搜索对象
        mSearch = RoutePlanSearch.newInstance();
        // 设置路径规划结果回调对象
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //option.setScanSpan(1000);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        //option.setOpenGps(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //option.setLocationNotify(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        // option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        //option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        //option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }




    /**
     * 定位回调
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            Log.i(TAG, location.getAddress().toString());
            Log.i(TAG, location.getDistrict());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            //只一次定位到当前位置并显示在地图中央
            if (isFirstLoc) {
                isFirstLoc = false;
                startLatLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(startLatLng);
                mBaiduMap.animateMapStatus(u);
            }

        }
    }

    /**
     * @param latLng 点击view中生成的坐标
     */
    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.clear();
        stopLatLng = null;
        // 构建Marker图标
        BitmapDescriptor bd = BitmapDescriptorFactory
                .fromResource(R.drawable.end_point);
        OverlayOptions option = new MarkerOptions().position(latLng).icon(bd);
        mBaiduMap.addOverlay(option);
        stopLatLng = latLng;
    }

    /**
     * @param mapPoi 点击view中生成的POI
     */
    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    /**
     * @param view 顶部三个view的点击
     */
    @OnClick({R.id.fg_baidu_plan, R.id.fg_baidu_nav, R.id.fg_baidu_pano})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_baidu_plan:
                if (startLatLng == null || stopLatLng == null) {
                    MyUtil.showToast(this.getActivity(), "请确保地图上有起点和终点");
                    return;
                }
                planWalkRoute(startLatLng, stopLatLng);
                break;
            case R.id.fg_baidu_nav:
                if (allSteplist == null) {
                    MyUtil.showToast(this.getActivity(), "请先规划路径");
                    return;
                }
                // BaiduNaviManager.getInstance().launchNavigator(getActivity(), bnlist, 1, true, new DemoRoutePlanListener());
                MyUtil.dialog("路径", stepSb.toString(), this.getActivity(), new MyUtil.OnDialogClickListener() {
                    @Override
                    public void dialogButton(boolean ispositve) {


                    }
                });
                break;
            case R.id.fg_baidu_pano:
                LatLng latLng = null;
                if (stopLatLng != null) {
                    latLng = stopLatLng;
                } else {
                    latLng = startLatLng;
                }
                Intent intent = new Intent();
                intent.putExtra("latLng", latLng);
                intent.setClass(this.getActivity(), BaiduPanoActivity.class);
                startActivity(intent);

                break;
        }
    }

    List<BNRoutePlanNode> bnlist;

    /**
     * 步行路径的规划
     *
     * @param start 起点
     * @param stop  终点
     */
    private void planWalkRoute(LatLng start, LatLng stop) {
        // 创建一个步行的路径规划的参数
        WalkingRoutePlanOption option = new WalkingRoutePlanOption();
        PlanNode startNode = PlanNode.withLocation(start);
        PlanNode endNode = PlanNode.withLocation(stop);
        option.from(startNode);
        option.to(endNode);
        mSearch.walkingSearch(option);
        Log.i(TAG, "startNode =" + startNode.getName());
        Log.i(TAG, "startNode =" + startNode.getName());

        //诱导界面需要的
        bnlist = new ArrayList<>();
        BNRoutePlanNode startbn = new BNRoutePlanNode(start.longitude, start.latitude, "", null);
        BNRoutePlanNode stopbn = new BNRoutePlanNode(stop.longitude, stop.latitude, "", null);
        bnlist.add(startbn);
        bnlist.add(stopbn);

    }

    private List<WalkingRouteLine.WalkingStep> allSteplist;
    StringBuilder stepSb;

    /**
     * @param walkingRouteResult 步行规划
     */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {// 处理有错误的情况
            MyUtil.showToast(this.getActivity(), "步行路径规划失败!");
            return;
        }
        //清除地图上的覆盖物
        mBaiduMap.clear();
        //获得路径
        List<WalkingRouteLine> lines = walkingRouteResult.getRouteLines();
        // 创建步行路线的覆盖物
        WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
        //使用第一条路径
        overlay.setData(lines.get(0));
        //添加到地图
        overlay.addToMap();
        //缩放到刚好显示整条路线
        overlay.zoomToSpan();

        //获得驾车路径上的途经点
        allSteplist = walkingRouteResult.getRouteLines().get(0).getAllStep();
        /* 获得该路线上可获取的所有的坐标
        List<LatLng> allLatLang = new ArrayList<>();
        for (int i = 0; i < allSteplist.size(); i++) {
            List<LatLng> l = allSteplist.get(i).getWayPoints();
            allLatLang.addAll(l);

        }*/
        stepSb = new StringBuilder();
        for (int i = 0; i < allSteplist.size(); i++) {
            //获得路线获取路段整体指示信息
            stepSb.append(allSteplist.get(i).getInstructions()).append("\n");
        }
        Log.i(TAG, stepSb.toString());

    }

    /**
     * @param transitRouteResult 公交
     */
    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    /**
     * @param drivingRouteResult 小车
     */

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    /**
     * @param bikingRouteResult 单车
     */
    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

/*    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener() {

            mBNRoutePlanNode = bnlist.get(0);
            Log.i(TAG,"DemoRoutePlanListener mBNRoutePlanNode ="+mBNRoutePlanNode.getLatitude());
            onJumpToNavigator();
        }

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            Log.i(TAG,"onJumpToNavigator");

            Intent intent = new Intent(getActivity(), BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ROUTE_PLAN_NODE", (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            MyUtil.showToast(getActivity(), "算路失败");
        }
    }*/
}
