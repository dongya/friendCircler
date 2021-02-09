package com.dongya.friendcircle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dongya.friendcircle.R;
import com.dongya.friendcircle.adapter.TweetAdatper;
import com.dongya.friendcircle.bean.TweentsItemBean;
import com.dongya.friendcircle.bean.TweetsBean;
import com.dongya.friendcircle.bean.UserInfoBean;
import com.dongya.friendcircle.http.DefaultObserver;
import com.dongya.friendcircle.http.HttpApi;
import com.dongya.friendcircle.utils.KeyboardUtil;
import com.dongya.friendcircle.utils.ScreenUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout linContent;
    private Toolbar rlParent;
    private EditText et_comment;
    private int titleHeight = 0;

    private TweetAdatper tweetAdatper;
    private List<TweentsItemBean> tweentsItemBeanList = new ArrayList<>();
    private List<TweetsBean> allDataList = new ArrayList<>();
    private int page = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initImmersionBar();
        getUserInfo();
        getTweets();
        handler = new Handler();
    }

    private void initImmersionBar(){
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(){
        titleHeight = ScreenUtil.dip2px(200);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerview);
        linContent = findViewById(R.id.ll_comment);
        et_comment = findViewById(R.id.et_comment);
        rlParent = findViewById(R.id.rl_parent);
        tweetAdatper = new TweetAdatper(mContext,tweentsItemBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tweetAdatper.bindToRecyclerView(recyclerView);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                tweentsItemBeanList.clear();
                allDataList.clear();
                getUserInfo();
                getTweets();
            }
        });
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.lan));
        tweetAdatper.setEnableLoadMore(true);
        tweetAdatper.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        handlerData();
                    }
                },1000);
            }
        },recyclerView);
        tweetAdatper.setAdapterItemClick(new TweetAdatper.AdapterItemClick() {
            @Override
            public void onParise() {

            }

            @Override
            public void onContent() {
                linContent.setVisibility(View.VISIBLE);
                et_comment.requestFocus();
                KeyboardUtil.showSoftInput(mContext);
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (linContent.getVisibility() == View.VISIBLE) {
                    linContent.setVisibility(View.GONE);
                    KeyboardUtil.hideSoftInput(MainActivity.this);
                    return true;
                }
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (totalDy <= titleHeight) {
                    float alpha = (float) totalDy / titleHeight;
                    rlParent.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mContext, R.color.colorPrimary), alpha));
                } else {
                    rlParent.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mContext, R.color.colorPrimary), 1));
                }
            }
        });
    }

    private void getUserInfo(){
        HttpApi.getUserInfo(new DefaultObserver<UserInfoBean>() {
            @Override
            public void onFail(int code, String msg) {

            }

            @Override
            public void onSuccess(UserInfoBean userInfoBean) {
                TweentsItemBean personInfo = new TweentsItemBean();
                personInfo.setName(userInfoBean.getUsername());
                personInfo.setAvatar(userInfoBean.getAvatar());
                personInfo.setType(TweetAdatper.Item_personInfo);
                tweentsItemBeanList.add(0,personInfo);
            }
        });
    }
    private void getTweets(){
        HttpApi.getTweets(new DefaultObserver<List<TweetsBean>>() {
            @Override
            public void onFail(int code, String msg) {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<TweetsBean> tweetsBeans) {
                refreshLayout.setRefreshing(false);
                allDataList.addAll(tweetsBeans);
                handlerData();
            }
        });
    }


    private void handlerData(){
        int start = page * 10;
        int end = (page*10+10) > allDataList.size()?allDataList.size():(page*10+10);
        List<TweetsBean> list = allDataList.subList(start,end);
        for (TweetsBean tweetsBean :list){
            if (TextUtils.isEmpty(tweetsBean.getContent()) && (tweetsBean.getImages() == null || tweetsBean.getImages().isEmpty())){
                continue;
            }
            createHeader(tweetsBean);
            createImage(tweetsBean);
            createConment(tweetsBean);

            TweentsItemBean pariseItem = new TweentsItemBean();
            pariseItem.setType(TweetAdatper.Item_parise);
            tweentsItemBeanList.add(pariseItem);

            TweentsItemBean dividerItem = new TweentsItemBean();
            dividerItem.setType(TweetAdatper.Item_divider);
            tweentsItemBeanList.add(dividerItem);
        }
        tweetAdatper.notifyDataSetChanged();
        if (end == allDataList.size()){
            tweetAdatper.loadMoreEnd(false);
        }else{
            tweetAdatper.loadMoreComplete();
        }
    }

    /**
     * 单条朋友圈中到头部信息
     * @param tweetsBean
     * @return
     */
    private void createHeader(TweetsBean tweetsBean){
        TweentsItemBean tweentsItemBean = new TweentsItemBean();
        if (tweetsBean.getSender() == null){
            return;
        }
        tweentsItemBean.setType(TweetAdatper.Item_header);
        tweentsItemBean.setAvatar(tweetsBean.getSender().getAvatar());
        tweentsItemBean.setName(tweetsBean.getSender().getUsername());
        tweentsItemBean.setContent(tweetsBean.getContent());
        if (tweentsItemBean != null){
            tweentsItemBeanList.add(tweentsItemBean);
        }
    }

    /**
     * 单条朋友圈中到图片信息
     * @param tweetsBean
     * @return
     */
    private void createImage(TweetsBean tweetsBean){
        TweentsItemBean tweentsItemBean = new TweentsItemBean();
        if (tweetsBean.getImages() == null || tweetsBean.getImages().isEmpty()){
            return;
        }
        tweentsItemBean.setType(TweetAdatper.Item_image);
        tweentsItemBean.setImageList(tweetsBean.getImages());
        if (tweentsItemBean != null){
            tweentsItemBeanList.add(tweentsItemBean);
        }
    }

    /**
     * 生成评论信息
     * @return
     */
    private void createConment(TweetsBean tweetsBean){
        if (tweetsBean.getComments() != null && !tweetsBean.getComments().isEmpty()){
            for (TweetsBean.CommentsBean commentsBean : tweetsBean.getComments()){
                TweentsItemBean tweentsItemBean = new TweentsItemBean();
                tweentsItemBean.setType(TweetAdatper.Item_content);
                tweentsItemBean.setContent(commentsBean.getContent());
                tweentsItemBean.setName(commentsBean.getSender().getUsername());
                tweentsItemBeanList.add(tweentsItemBean);
            }
        }
    }
}
