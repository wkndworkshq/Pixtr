package com.tbg.pixtr.home.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.view.CollectionDetailActivity;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.home.adapter.HomeAdapter;
import com.tbg.pixtr.home.presenter.HomePresenter;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.settings.views.SettingsActivity;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.HomeItemDecorator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeView, HomeAdapter.ActivityInteractions {

    @Inject
    HomePresenter presenter;

    @Inject
    HomeAdapter adapter;

    @Inject
    HomeItemDecorator itemDecorator;

    @Inject
    DefaultItemAnimator itemAnimator;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collections)
    RecyclerView collections;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        super.setStatusFlags(statusFlags.TransparentStatusBar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public HomePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }

    @Override
    public void setupView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        collections.setLayoutManager(layoutManager);
        collections.setItemAnimator(itemAnimator);
        adapter.setOnActivityInteraction(this);
        collections.setAdapter(adapter);
        collections.addOnScrollListener(toolbarElevation);
    }

    @Override
    public void deliverData(List<CollectionsPojo> data) {
        adapter.updateData(data);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    /**
     * Recyclerview scroll helper method.
     */
    private RecyclerView.OnScrollListener toolbarElevation = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findFirstVisibleItemPosition() == 0
                    && layoutManager.findViewByPosition(0).getTop() == collections.getPaddingTop()
                    && toolbar.getTranslationZ() != 0) {
                toolbar.setTranslationZ(0f);
            } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                    && toolbar.getTranslationZ() != -1f) {
                toolbar.setTranslationZ(-1f);
            }
        }
    };


    /**
     * Activity interaction interface.
     *
     * @param position
     */
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, CollectionDetailActivity.class);
        intent.putExtra(AppConstants.INTENT_KEY_COLLECTION_ID, adapter.getId(position));
        intent.putExtra(AppConstants.INTENT_KEY_COLLECTION_DATA, new Gson().toJson(adapter.getData(position)));
        startActivity(intent);
    }


    /**
     * Menu Skeleton methods.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about_tbg) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.tbg.co"));
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
