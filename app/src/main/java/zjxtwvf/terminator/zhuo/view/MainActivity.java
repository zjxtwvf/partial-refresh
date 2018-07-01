package zjxtwvf.terminator.zhuo.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import zjxtwvf.terminator.zhuo.Entity.PreLoadEntity;
import zjxtwvf.terminator.zhuo.R;
import zjxtwvf.terminator.zhuo.adapter.PreloadUrlAdapter;
import zjxtwvf.terminator.zhuo.utils.UIUtils;

public class MainActivity extends AppCompatActivity {
    WebView webView ;
    ListView mPreloadUrl;
    PreloadUrlAdapter mAdapter;
    List<PreLoadEntity> mData = new ArrayList<PreLoadEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        inttEvent();
    }

    public void initView(){
        mPreloadUrl = (ListView)findViewById(R.id.lv_preload_url);
        mAdapter = new PreloadUrlAdapter(mData);
        mPreloadUrl.setAdapter(mAdapter);
        webView  = new WebView(this);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //刷新ITEM状态（不得刷新listview）
                refreshListViewItem(url);
            }
        });
    }

    public void initData() {
        mData.add(new PreLoadEntity("https://image.guazistatic.com/gz01180312/18/10/3e61da07c3793f3e39d99c0de3212db9.jpg@base@tag=imgScale","未加载"));
        mData.add(new PreLoadEntity("http://app2-jr.guazi.com/cd?jr_from=homepagemodule1&platform=app&ca_s=app_tg&ca_n=meizu","未加载"));
        mData.add(new PreLoadEntity("https://sta.guazi.com/baomai/baomai-index.html#index?domain=cd&city_name=%E6%88%90%E9%83%BD&ca_s=app_tg&ca_n=meizu","未加载"));
        mData.add(new PreLoadEntity("https://m.maodou.com?ca_s=xcsop_guaziapp&ca_n=smallpindao","未加载"));
        mData.add(new PreLoadEntity("https://m.maodou.com?ca_s=xcsop_guaziapp&ca_n=bigdf","未加载"));
        mData.add(new PreLoadEntity("https://app2-jr.guazi.com/loan_v2/webank_pre_apply?platform=wap&jr_from=c2c_ceyice_a&ca_s=app_tg&ca_n=meizu","未加载"));
        mData.add(new PreLoadEntity("https://sta.guazi.com/baomai/baomai-index.html#index?domain=cd&city_name=%E6%88%90%E9%83%BD&ca_s=app_tg&ca_n=meizu","未加载"));
        mData.add(new PreLoadEntity("https://m.maodou.com/www/fyc/brifinfo/wQC?ca_s=xcsop_guaziapp&ca_n=smalldf","未加载"));
        mData.add(new PreLoadEntity("guazi://openapi/openTab?tab=2&filterObject=%7B%22tag_types%22:%7B%22selectType%22:%22multi%22,%22filterValue%22:%5B%7B%22value%22:%226%22,%22name%22:%22%E5%8F%AF%E8%BF%81%E5%85%A8%E5%9B%BD%22%7D%5D%7D%7D","未加载"));
        mData.add(new PreLoadEntity("https://app2-jr.guazi.com/cd?jr_from=operation&platform=app&ca_s=app_tg&ca_n=meizu","未加载"));
        mData.add(new PreLoadEntity("guazi://openapi/openTab?tab=2&filterObject=%7B%7D","未加载"));
        mData.add(new PreLoadEntity("https://guaziapp-m.guazi.com/cd/intro?ca_s=app_tg&ca_n=meizu","未加载"));

    }

    public void inttEvent() {
        mPreloadUrl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",mData.get(index).getUrl());
                intent.putExtra("title","六一儿童节");
                MainActivity.this.startActivity(intent);
            }
        });

        mPreloadUrl.setOnScrollListener(new AbsListView.OnScrollListener() {
            Timer timer = null;
            TimerTask timerTask = null;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
                //停止滑动 超过三秒预加载URL
                if(AbsListView.OnScrollListener.SCROLL_STATE_IDLE == state){
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            preLoadUrl();
                        }
                    };
                    timer.schedule(timerTask,3000);
                }else if(AbsListView.OnScrollListener.SCROLL_STATE_FLING == state){
                    if(null != timer){
                        timer.cancel();
                        timer = null;
                    }
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
    }

    private void refreshListViewItem(String url) {
        int index = 0;
        for(int i=0;i<mData.size();i++){
            if(url.equals(mData.get(i).getUrl())){
                index = i;
                break;
            }
        }
        int first = mPreloadUrl.getFirstVisiblePosition();
        int last = mPreloadUrl.getLastVisiblePosition();
        if(first <= index && index<= last){
            View view = mPreloadUrl.getChildAt(index - first);
            if(null != view){
                mAdapter.updateItemView(view);
            }
        }
        mData.get(index).setFlag("已预加载");
    }

    private void preLoadUrl() {
        final int first = mPreloadUrl.getFirstVisiblePosition();
        final int last = mPreloadUrl.getLastVisiblePosition();
        for(int i=0;i < (last-first+1);i++){
            //预加载可视item的URL
            if(!mData.get(first + i).getFlag().equals("已预加载")){
                final int index = i;
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(mData.get(first + index).getUrl());
                    }
                });
            }
        }
    }
}
