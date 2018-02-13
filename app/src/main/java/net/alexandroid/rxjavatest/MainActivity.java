package net.alexandroid.rxjavatest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private String name = "kuku";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just();
        //fromCalable();
        //observableOnSubscribe();
        //observableOnSubscribeDisposableObserver();

        Observable<String> observable = Observable
                .fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return getName();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String pS) throws Exception {
                        return pS + "_test";
                    }
                });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("ZAQ", "onSubscribe");
            }

            @Override
            public void onNext(String pS) {
                Log.d("ZAQ", "onNext: " + pS);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ZAQ", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("ZAQ", "onComplete");
            }
        });


    }

    private void observableOnSubscribeDisposableObserver() {
        Observable<String> stringObservable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.d("ZAQ", "setCancellable#cancel");
                    }
                });

                emitter.onNext("ha ha 1");
                emitter.onNext("ha ha 2");
                emitter.onNext("ha ha 3");
                //emitter.onComplete();
            }
        });

        Disposable disposable = stringObservable1.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String pS) {
                Log.d("ZAQ", "onNext: " + pS);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ZAQ", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("ZAQ", "onComplete");
            }
        });

        disposable.dispose();

    }

    private void observableOnSubscribe() {
        Observable<String> stringObservable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.d("ZAQ", "setCancellable#cancel");
                    }
                });

                emitter.onNext("ha ha 1");
                emitter.onNext("ha ha 2");
                emitter.onNext("ha ha 3");
                //emitter.onComplete();
            }
        });

        stringObservable1.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("ZAQ", "onSubscribe");
                d.dispose();
            }

            @Override
            public void onNext(String pS) {
                Log.d("ZAQ", "onNext: " + pS);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ZAQ", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("ZAQ", "onComplete");
            }
        });


    }

    private void just() {
        Observable<String> test = Observable.just("Test");
    }

    private void fromCalable() {
        Observable<String> stringObservable = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getName();
            }
        });

        stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("ZAQ", "onSubscribe");
            }

            @Override
            public void onNext(String pS) {
                Log.d("ZAQ", "onNext: " + pS);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ZAQ", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("ZAQ", "onComplete");
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }
}
