package com.test.atostestapp.data;


import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Observable<Resource<ResultType>> result;

    @MainThread
    protected NetworkBoundResource() {
        Observable<Resource<ResultType>> source;
        source = createCall()
                .subscribeOn(Schedulers.io())
                .doOnNext(apiResponse -> saveCallResult(processResponse(apiResponse)))
                .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                .doOnError(t -> onFetchFailed())
                .onErrorResumeNext(t -> {
                    return loadFromDb()
                            .toObservable()
                            .map(data -> Resource.error(t.getMessage(), data));

                })
                .observeOn(AndroidSchedulers.mainThread());

        result = Observable.concat(
                loadFromDb()
                        .toObservable()
                        .map(Resource::loading)
                        .take(1),
                source
        );
    }

    public Observable<Resource<ResultType>> getAsObservable() {
        return result;
    }

    protected void onFetchFailed() {
    }

    @WorkerThread
    protected RequestType processResponse(Resource<RequestType> response) {
        return response.data;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Observable<Resource<RequestType>> createCall();
}