package muchbeer.raum.roomrxjava.DataSource;

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
    public Flowable<User> getUser() {
        return mUserDao.getUser();
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
