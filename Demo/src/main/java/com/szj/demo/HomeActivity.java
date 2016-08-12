package com.szj.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;
import com.szj.demo.frag.BaiduFragment;
import com.szj.demo.frag.NewsFragment;


/**
 *
 *
 * 760042989
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

      */
        initViews();
    }

    private FragmentManager manager;
    private NewsFragment newsFragment;
    private BaiduFragment baiduFragment;

    private void initViews() {
        //新闻
        findViewById(R.id.home_bottom_news_bt).setOnClickListener(this);

        //百度地图
        findViewById(R.id.home_bottom_baidu_bt).setOnClickListener(this);

        //得到片段
        manager=getSupportFragmentManager();




        //实例化
        newsFragment =new NewsFragment();
        baiduFragment= new BaiduFragment();

        //得到事务
        FragmentTransaction transaction =manager.beginTransaction();

        transaction.add(R.id.home_fl,newsFragment,"one");
        transaction.add(R.id.home_fl,baiduFragment,"two");

        hideFragment(transaction);



        transaction.show(newsFragment);
        //将事务加入回退栈
        transaction.addToBackStack(null);
        //提交事务
        transaction.commit();


    }

    private void hideFragment(FragmentTransaction transaction) {
        transaction.hide(newsFragment);
        transaction.hide(baiduFragment);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction =manager.beginTransaction();
        hideFragment(transaction);
        Log.i(TAG,"onClick");
        switch (v.getId()){
            case R.id.home_bottom_news_bt:
                transaction.show(newsFragment);
                transaction.commit();
                break;
            case R.id.home_bottom_baidu_bt:
                transaction.show(baiduFragment);
                transaction.commit();
                Log.i(TAG,"onClick");
                break;
            default:

                break;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
