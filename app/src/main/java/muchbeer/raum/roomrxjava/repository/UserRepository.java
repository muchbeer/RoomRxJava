package muchbeer.raum.roomrxjava.repository;

import android.app.Application;
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
import muchbeer.raum.roomrxjava.DB.UserDatabase;
import muchbeer.raum.roomrxjava.DataSource.UserDataSouce;
import muchbeer.raum.roomrxjava.model.User;

public class UserRepository {


    private static final String LOG_TAG = UserRepository.class.getSimpleName();
    private Application application;
  ///  UserDatabase database = UserDatabase.getInstance(context);
    private UserDatabase  userAppDatabase;
    private CompositeDisposable disposable=new CompositeDisposable();
    private MutableLiveData<List<User>> userLiveData=new MutableLiveData<>();
    private long rowIdOfTheItemInserted;

    public UserRepository(Application application) {
        this.application = application;
       // contactsAppDatabase= Room.databaseBuilder(application.getApplicationContext(),ContactsAppDatabase.class,"ContactDB").build();
        userAppDatabase = UserDatabase.getInstance(application);


        disposable.add(userAppDatabase.userDao().getUser()
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
    /*    disposable.add(userAppDatabase.userDao().getUser()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Exception {
                                userLiveData.postValue((List<User>) user);
                                Log.d(LOG_TAG, "The user in the database are: " + user);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d(LOG_TAG, "The error comes out is: " + throwable);
                            }
                        }));*/
    }

    public void newUser(final String userName, final String userSchool, final String userPlace) {
        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                //  rowIdOfTheItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0,name, email));
                rowIdOfTheItemInserted = userAppDatabase.userDao().insertUserMvvm(new User(userName, userSchool, userPlace));

            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Toast.makeText(application.getApplicationContext()," user added successfully "+rowIdOfTheItemInserted, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError(Throwable e) {
                Log.d(LOG_TAG, "tHE new error on create the new user is: " + e.getMessage());
                }
            }));
    }

    public void updateUser(final User user) {

        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                userAppDatabase.userDao().updateUser(user);
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Toast.makeText(application.getApplicationContext()," user updated successfully ", Toast.LENGTH_LONG).show();
                    Log.d(LOG_TAG, "The complete value edite is : " +user);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(application.getApplicationContext()," error occurred ", Toast.LENGTH_LONG).show();
                        Log.d(LOG_TAG, "The error is : " + e.getMessage());
                }
            }));
    }

    public void deleteUser(final User user) {

        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
            userAppDatabase.userDao().deleteUserMvvm(user);
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Toast.makeText(application.getApplicationContext()," user deleted successfully ", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(application.getApplicationContext()," user error ", Toast.LENGTH_LONG).show();
                     Log.d(LOG_TAG, "The error obtain is: " +e.getMessage());
                }
            }));
    }

     public void clear() {
        disposable.clear();
    }

    public MutableLiveData<List<User>> getUserLiveData() { return userLiveData; }

}
