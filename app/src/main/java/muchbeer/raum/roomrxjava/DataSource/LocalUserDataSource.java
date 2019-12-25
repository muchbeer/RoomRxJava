package muchbeer.raum.roomrxjava.DataSource;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import muchbeer.raum.roomrxjava.DB.UserDao;
import muchbeer.raum.roomrxjava.DB.UserDatabase;
import muchbeer.raum.roomrxjava.model.User;
import muchbeer.raum.roomrxjava.repository.UserRepository;

public class LocalUserDataSource implements UserDataSouce {

    private static final String LOG_TAG = LocalUserDataSource.class.getSimpleName();
    private UserDatabase userAppDatabase;
    private CompositeDisposable disposable=new CompositeDisposable();
    private MutableLiveData<List<User>> userLiveData=new MutableLiveData<>();
    private long rowIdOfTheItemInserted;
    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;

        disposable.add(userDao.getUser()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        userLiveData.postValue(users);
                        Log.d(LOG_TAG, "The user in the database are: " + users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(LOG_TAG, "The error comes out is: " + throwable);
                    }
                }));
    }

    @Override
    public void newUser(final String userName, final String userSchool, final String userPlace) {
        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                //  rowIdOfTheItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0,name, email));
                rowIdOfTheItemInserted = userAppDatabase.userDao().insertUserMvvm(new User(userName, userSchool, userPlace));

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        //Toast.makeText(Context.getApplicationContext()," user added successfully "+rowIdOfTheItemInserted, Toast.LENGTH_LONG).show();
                        Log.d(LOG_TAG, "The user is added successful");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, "tHE new error on create the new user is: " + e.getMessage());

                    }
                }));
    }

    @Override
    public void updateUser(final User user) {
        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.updateUser(user);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "The user is updatwe successful");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "tHE new error on create the new user is: " + e.getMessage());
            }
        }));
    }

    @Override
    public MutableLiveData<List<User>> getUserLiveData() { return userLiveData; }
    @Override
    public void clear() { disposable.clear();  }

    @Override
    public void deleteUser(final User user) {
        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.deleteUserMvvm(user);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG, "The user is delete successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, "tHE new error on the delete: " + e.getMessage());
                    }
                }));
    }
}
