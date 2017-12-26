package com.tbg.pixtr.model.manager;

import com.tbg.pixtr.model.api.NetworkingInterface;
import com.tbg.pixtr.model.pojo.collection_images.CollectionDetailsPojo;
import com.tbg.pixtr.model.pojo.collections.CollectionsPojo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.QueryMap;

/**
 * Created by kausthubhadhikari on 23/08/17.
 */

public class NetworkManager {

    private NetworkingInterface networkInterface;

    public NetworkManager(NetworkingInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    /**
     * Get Collections.
     *
     * @param options
     * @return
     */
    public Observable<List<CollectionsPojo>> getCollections(@QueryMap Map<String, String> options) {
        return networkInterface.getCollections(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get collection photos.
     *
     * @param pathId
     * @param options
     * @return
     */
    public Observable<List<CollectionDetailsPojo>> getCollectionDetails(String pathId, @QueryMap Map<String, String> options) {
        return networkInterface.getCollectionPhotos(pathId, options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
