package com.tbg.pixtr.collection_detail.view;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.adapter.CollectionAdapter;
import com.tbg.pixtr.collection_detail.adapter.viewholder.CollectionViewholder;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionDetailActivity extends BaseActivity implements CollectionDetailView, DiscreteScrollView.ScrollListener<CollectionViewholder>, DiscreteScrollView.OnItemChangedListener<CollectionViewholder>, CollectionAdapter.OnClickListener {

    @Inject
    CollectionDetailPresenter presenter;

    private String collectionId;

    @BindView(R.id.collectionImages)
    DiscreteScrollView discreteScrollView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    CollectionAdapter adapter;

    @Inject
    ArgbEvaluator evaluator;

    private int currentOverlayColor;

    private int overlayColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_detail);
        ButterKnife.bind(this);
        super.setStatusFlags(statusFlags.TransparentStatusBarAndNavigationBar);
        super.onCreate(savedInstanceState);
        collectionId = getIntent().getStringExtra(AppConstants.INTENT_KEY_COLLECTION_ID);
        presenter.requestCollectionDetails();
        currentOverlayColor = ContextCompat.getColor(this, R.color.currentItemOverlay);
        overlayColor = ContextCompat.getColor(this, R.color.itemOverlay);
    }

    @Override
    public void inject(Injector injector) {
        injector.inject(this);
    }

    @Override
    public CollectionDetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onNetworkError(Throwable throwable) {

    }

    @Override
    public void setupView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter.setOnClickListener(this);
        discreteScrollView.setAdapter(adapter);
        discreteScrollView.addScrollListener(this);
        discreteScrollView.addOnItemChangedListener(this);
    }

    @Override
    public void onDeliverData(List<CollectionDetailsPojo> data) {
        adapter.updateData(data);
        discreteScrollView.scrollToPosition(1);
    }

    @Override
    public String getId() {
        return collectionId;
    }

    @Override
    public void hideProgress() {
        discreteScrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        discreteScrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCurrentItemChanged(CollectionViewholder collectionViewholder, int adapterPosition) {
        if (collectionViewholder != null) {
            collectionViewholder.setOverlayColor(currentOverlayColor);
        }
    }

    @Override
    public void onScroll(float currentPosition, int currentIndex, int newIndex, CollectionViewholder collectionViewholder, CollectionViewholder newCollectionViewholder) {
        if (collectionViewholder != null && newCollectionViewholder != null) {
            float position = Math.abs(currentPosition);
            collectionViewholder.setOverlayColor(interpolate(position, currentOverlayColor, overlayColor));
            newCollectionViewholder.setOverlayColor(interpolate(position, overlayColor, currentOverlayColor));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private int interpolate(float fraction, int c1, int c2) {
        return (int) evaluator.evaluate(fraction, c1, c2);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }
}
