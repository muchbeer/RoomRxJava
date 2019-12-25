package muchbeer.raum.roomrxjava.DataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.model.User;

public interface UserDataSouce {


    MutableLiveData<List<User>> getUserLiveData();

   void newUser(String userName, String userSchool, String userPlace);

       void deleteUser(User user);

    void updateUser(User user);

    void clear();
}
