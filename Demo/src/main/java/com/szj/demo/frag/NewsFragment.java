package com.szj.demo.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gxz.PagerSlidingTabStrip;
import com.szj.demo.R;
import com.szj.demo.adapter.NewsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    public NewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View v) {
        FragmentManager manager =getFragmentManager();

        PagerSlidingTabStrip pagerSlidingTabStrip= (PagerSlidingTabStrip)v.findViewById(R.id.fg_news_tp);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.fg_news_vp);
        viewPager.setAdapter(new NewsAdapter(manager));
        pagerSlidingTabStrip.setViewPager(viewPager);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ButterKnife.reset(this);
    }
}
