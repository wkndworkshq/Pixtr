package com.tbg.pixtr.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tbg.pixtr.R;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.home.adapter.HomeAdapter;
import com.tbg.pixtr.home.presenter.HomePresenter;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
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
        collections.setLayoutManager(layoutManager);
        collections.setItemAnimator(itemAnimator);
        //collections.addItemDecoration(itemDecorator);
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

    }

    @Override
    public void onPaletteGeneration(Drawable drawable, ImageView view) {
        presenter.generatePallete(drawable, view);
    }
}
