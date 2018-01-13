package com.tbg.pixtr.collection_detail.view;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.adapter.CollectionAdapter;
import com.tbg.pixtr.collection_detail.adapter.viewholder.CollectionViewholder;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.custom.CustomTypefaceSpan;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.name)
    AppCompatTextView name;

    @BindView(R.id.images)
    AppCompatTextView noImages;

    @BindView(R.id.placeholder_text)
    AppCompatTextView listBottomHolder;

    @BindView(R.id.autoUpdate)
    ImageButton autoUpdate;

    @Inject
    CollectionAdapter adapter;

    @Inject
    ArgbEvaluator evaluator;

    @Inject
    SharedPreferencesUtil preferencesUtil;

    private int currentOverlayColor;

    private int overlayColor;

    private CollectionsPojo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_detail);
        ButterKnife.bind(this);
        super.setStatusFlags(statusFlags.TransparentStatusBarAndNavigationBar);
        collectionId = getIntent().getStringExtra(AppConstants.INTENT_KEY_COLLECTION_ID);
        data = new Gson().fromJson(getIntent().getStringExtra(AppConstants.INTENT_KEY_COLLECTION_DATA), CollectionsPojo.class);
        super.onCreate(savedInstanceState);
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
        SpannableString spannableString = new SpannableString(getString(R.string.collection_details_placholder));
        Typeface regTypeface = ResourcesCompat.getFont(this, R.font.montserrat);
        Typeface boldTypeface = ResourcesCompat.getFont(this, R.font.montserrat_bold);
        spannableString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, regTypeface), 0, 1, 0);
        spannableString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, boldTypeface), 2, spannableString.length(), 0);
        listBottomHolder.setText(spannableString);
        autoUpdate.setImageResource(preferencesUtil.getAutoUpdateId().equals("" + data.id) ? R.drawable.ic_followed : R.drawable.ic_follow_collection);

        name.setText(data.title);
        noImages.setText(data.totalPhotos + " Photos");

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
        intent.putExtra(AppConstants.INTENT_DETAILS_DATA, new Gson().toJson(adapter.getData(position)));
        startActivity(intent);
    }

    @OnClick({R.id.autoUpdate})
    public void onClick(View view) {
        if (view.getId() == R.id.autoUpdate) {
            if (!preferencesUtil.getAutoUpdateId().equals("" + data.id)) {
                preferencesUtil.clearData();
                preferencesUtil.saveAutoUpdateId(data.id);
            } else {
                preferencesUtil.clearData();
            }
            autoUpdate.setImageResource(preferencesUtil.getAutoUpdateId().equals("" + data.id) ? R.drawable.ic_followed : R.drawable.ic_follow_collection);
        }
    }
}
