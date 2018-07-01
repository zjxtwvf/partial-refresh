package zjxtwvf.terminator.zhuo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import zjxtwvf.terminator.zhuo.Entity.PreLoadEntity;
import zjxtwvf.terminator.zhuo.R;
import zjxtwvf.terminator.zhuo.utils.UIUtils;

public class PreloadUrlAdapter extends BaseAdapter {

    private List<PreLoadEntity> mData;

    public PreloadUrlAdapter(List<PreLoadEntity> mData){
        this.mData = mData;
    }

    @Override
    public int getCount() {
        if(null != mData){
            return mData.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View contentView, ViewGroup viewGroup) {
        PreloadUrlHolder preloadUrlHolder;
        if(null == contentView){
            View view = UIUtils.inflate(R.layout.list_item);
            preloadUrlHolder = new PreloadUrlHolder();
            preloadUrlHolder.url = view.findViewById(R.id.tv_url);
            preloadUrlHolder.flag = view.findViewById(R.id.tv_flag);
            contentView = view;
            contentView.setTag(preloadUrlHolder);
        }else{
            preloadUrlHolder = (PreloadUrlHolder)contentView.getTag();
        }

        preloadUrlHolder.url.setText(mData.get(index).getUrl());
        preloadUrlHolder.flag.setText(mData.get(index).getFlag());

        return contentView;
    }

    public void updateItemView(View itemView){
        final PreloadUrlHolder preloadUrlHolder = (PreloadUrlHolder)itemView.getTag();
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                preloadUrlHolder.flag.setText("已预加载");
            }
        });
    }

    public static class PreloadUrlHolder{
        TextView url;
        TextView flag;
    }
}
