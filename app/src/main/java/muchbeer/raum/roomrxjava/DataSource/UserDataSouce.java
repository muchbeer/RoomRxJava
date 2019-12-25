package muchbeer.raum.roomrxjava.DataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.model.User;

public interface UserDataSouce {


    Flowable<List<User>> getUser();

    MutableLiveData<List<User>> getUserLiveData();

    Completable insertOrUpdateUser(User user);

    void deleteAllUsers();
}
