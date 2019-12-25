package muchbeer.raum.roomrxjava.DataSource;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.DB.UserDao;
import muchbeer.raum.roomrxjava.model.User;

public class LocalUserDataSource implements UserDataSouce {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Flowable<List<User>> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public MutableLiveData<List<User>> getUserLiveData() {
        return null;
    }

    @Override
    public Completable insertOrUpdateUser(User user) {
        return mUserDao.insertUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
}
