package com.tbg.pixtr.home.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
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
import com.tbg.pixtr.utils.custom.CustomTypefaceSpan;
import com.tbg.pixtr.utils.misc.AppConstants;
import com.tbg.pixtr.utils.misc.HomeItemDecorator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.error_placeholder)
    ConstraintLayout errorPlaceholder;

    private int page = 1;

    private boolean loading = false;

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
        loading = false;
        page = (page == 0 ? 0 : page - 1);
        if (adapter.getItemCount() == 0) {
            errorPlaceholder.setVisibility(View.VISIBLE);
        }
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
        if (errorPlaceholder.getVisibility() == View.VISIBLE) {
            errorPlaceholder.setVisibility(View.GONE);
        }
        adapter.updateData(data);
        loading = false;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }


    /**
     * Scroll listener for the recyclerview.
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

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            boolean endReachedFlag = lastVisibleItem + 5 >= totalItemCount;
            if (totalItemCount > 0 && endReachedFlag && !loading) {
                loading = true;
                page = page + 1;
                presenter.retrieveCollections(page, false);
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
        Typeface menuTypeface = ResourcesCompat.getFont(this, R.font.montserrat);

        SpannableString aboutUsString = new SpannableString(getString(R.string.menu_item_about));
        aboutUsString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, menuTypeface), 0, aboutUsString.length(), 0);

        SpannableString settingsString = new SpannableString(getString(R.string.menu_item_settings));
        settingsString.setSpan(new CustomTypefaceSpan(AppConstants.CUSTOM_FONT_FAMILY, menuTypeface), 0, settingsString.length(), 0);

        MenuItem about_menu_item = menu.findItem(R.id.menu_about_tbg);
        MenuItem settingsMenuItem = menu.findItem(R.id.menu_settings);

        about_menu_item.setTitle(aboutUsString);
        settingsMenuItem.setTitle(settingsString);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about_tbg) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.tbglabs.com"));
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_settings) {
            finish();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @OnClick({R.id.reloadData})
    public void onClick(View view) {
        if (view.getId() == R.id.reloadData) {
            if (!loading) {
                loading = true;
                presenter.retrieveCollections(page, true);
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


