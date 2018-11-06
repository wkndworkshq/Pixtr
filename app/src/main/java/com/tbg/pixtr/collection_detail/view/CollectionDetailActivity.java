package com.tbg.pixtr.collection_detail.view;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.gson.Gson;
import com.tbg.pixtr.R;
import com.tbg.pixtr.collection_detail.adapter.CollectionAdapter;
import com.tbg.pixtr.collection_detail.adapter.viewholder.CollectionViewholder;
import com.tbg.pixtr.collection_detail.presenter.CollectionDetailPresenter;
import com.tbg.pixtr.db.preferences.SharedPreferencesUtil;
import com.tbg.pixtr.detail.view.DetailActivity;
import com.tbg.pixtr.di.injector.Injector;
import com.tbg.pixtr.jobs.WallpaperJob;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;
import com.tbg.pixtr.utils.base.BaseActivity;
import com.tbg.pixtr.utils.custom.CustomTypefaceSpan;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @BindView(R.id.error_placeholder)
    ConstraintLayout errorPlaceholder;

    @Inject
    CollectionAdapter adapter;

    @Inject
    ArgbEvaluator evaluator;

    @Inject
    SharedPreferencesUtil preferencesUtil;

    private int currentOverlayColor;

    private int overlayColor;

    private CollectionsPojo data;

    private int page = 1;

    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_detail);
        ButterKnife.bind(this);
        super.setStatusFlags(statusFlags.TransparentStatusBarAndNavigationBar);
        collectionId = getIntent().getStringExtra(AppConstants.INTENT_KEY_COLLECTION_ID);
        data = new Gson().fromJson(getIntent().getStringExtra(AppConstants.INTENT_KEY_COLLECTION_DATA), CollectionsPojo.class);
        super.onCreate(savedInstanceState);
        presenter.requestCollectionDetails(page, true);
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
        loading = false;
        page = (page == 0 ? 0 : page - 1);
        if (adapter.getItemCount() == 0) {
            errorPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setupView() {
        SpannableString spannableString = new SpannableString(getString(R.string.collection_details_placholder));
        Typeface regTypeface = ResourcesCompat.getFont(this, R.font.montserrat);
        Typeface boldTypeface = ResourcesCompat.getFont(this, R.font.montserrat_bold);
        spannableString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, regTypeface), 0, 12, 0);
        spannableString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, boldTypeface), 13, spannableString.length(), 0);
        listBottomHolder.setText(spannableString);

        name.setText(data.title);
        noImages.setText(getResources().getQuantityString(R.plurals.photos, data.totalPhotos, data.totalPhotos));

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
        if (errorPlaceholder.getVisibility() == View.VISIBLE) {
            errorPlaceholder.setVisibility(View.GONE);
        }
        adapter.updateData(data);
        loading = false;
        if (listBottomHolder.getVisibility() == View.GONE) {
            listBottomHolder.setVisibility(View.VISIBLE);
        }
        if (!preferencesUtil.getTutorialLoaded()) {
            autoUpdateTutorial();
        }
    }

    @Override
    public String getId() {
        return collectionId;
    }

    @Override
    public void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            discreteScrollView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
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
        int totalItemCount = discreteScrollView.getLayoutManager().getItemCount();
        boolean endReachedFlag = currentIndex + 4 >= totalItemCount;
        if (totalItemCount > 0 && endReachedFlag && !loading) {
            loading = true;
            page = page + 1;
            presenter.requestCollectionDetails(page, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_detail_menu, menu);
        MenuItem autoUpdate = menu.findItem(R.id.follow_menu_item);
        autoUpdate.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.follow_menu_item) {
                clearScheduledJobs();
                if (!preferencesUtil.getAutoUpdateId().equals("" + data.id)) {
                    preferencesUtil.clearAutoUpdateData();
                    preferencesUtil.saveAutoUpdateId(data.id);
                    scheduleUpdateJob();
                } else {
                    preferencesUtil.clearAutoUpdateData();
                }
                autoUpdate.setIcon(preferencesUtil.getAutoUpdateId().equals("" + data.id) ? R.drawable.ic_followed : R.drawable.ic_follow_collection);
            }
            return true;
        });
        autoUpdate.setIcon(preferencesUtil.getAutoUpdateId().equals("" + data.id) ? R.drawable.ic_followed : R.drawable.ic_follow_collection);
        return super.onCreateOptionsMenu(menu);
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


    /**
     * Display the tutorial logic code.
     */
    public void autoUpdateTutorial() {
        TapTargetView.showFor(this,
                TapTarget.forToolbarMenuItem(toolbar, R.id.follow_menu_item, "Auto Update", "Enable auto-update to change wallpapers everyday.")
                        .outerCircleColor(R.color.colorAccent)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.colorPrimary)
                        .titleTextSize(25)
                        .titleTextColor(R.color.colorPrimaryDark)
                        .descriptionTextSize(15)
                        .descriptionTextColor(R.color.detailSecondaryTextColor)
                        .textColor(R.color.colorPrimary)
                        .textTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold))
                        .dimColor(R.color.itemOverlay)
                        .drawShadow(true)
                        .transparentTarget(true)
                        .cancelable(false)
                        .tintTarget(true)
                        .transparentTarget(false)
                        .targetRadius(60),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        preferencesUtil.setTutorialLoaded();
                    }
                });
    }


    /**
     * Schedule the job.
     */
    public void scheduleUpdateJob() {
        new JobRequest.Builder(WallpaperJob.TAG)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setPeriodic(TimeUnit.HOURS.toMillis(AppConstants.JOB_PERIODIC_HOURS), TimeUnit.HOURS.toMillis(AppConstants.JOB_FLEX_HOURS))
                .build()
                .schedule();
    }


    /**
     * Clear the Wallpaper jobs.
     */
    public void clearScheduledJobs() {
        if (!JobManager.instance().getAllJobRequestsForTag(WallpaperJob.TAG).isEmpty()) {
            JobManager.instance().cancelAllForTag(WallpaperJob.TAG);
        }
    }

    @OnClick({R.id.reloadData})
    public void onClick(View view) {
        if (view.getId() == R.id.reloadData) {
            if (!loading) {
                loading = true;
                presenter.requestCollectionDetails(page, true);
            }
        }
    }

    /**
     * TODO Need to handle config changes and resizing.
     *
     * @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    }

     @Override protected void onSaveInstanceState(Bundle outState) {
     super.onSaveInstanceState(outState);

     }**/

}
