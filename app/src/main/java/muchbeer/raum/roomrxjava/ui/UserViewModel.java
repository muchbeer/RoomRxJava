package muchbeer.raum.roomrxjava.ui;

import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.DataSource.UserDataSouce;
import muchbeer.raum.roomrxjava.model.User;

public class UserViewModel extends ViewModel {

    private final UserDataSouce mDataSource;

    private User mUser;

    public UserViewModel(UserDataSouce mDataSource) {
        this.mDataSource = mDataSource;
    }

  /*  public Flowable<String> getUserName() {
        return mDataSource.getUser()
                // for every emission of the user, get the user name
                .map(user -> {
                    mUser = user;
                    return user.getUserName();
                });

    }*/

/*    public Completable updateUserName(final String userName) {
        // if there's no user, create a new user.
        // if we already have a user, then, since the user object is immutable,
        // create a new user, with the id of the previous user and the updated user name.
        mUser = mUser == null
                ? new User(userName)
                : new User(mUser.getId(), userName);
        return mDataSource.insertOrUpdateUser(mUser);
    }*/

}
