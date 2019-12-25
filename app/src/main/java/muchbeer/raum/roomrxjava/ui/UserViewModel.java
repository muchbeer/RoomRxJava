package muchbeer.raum.roomrxjava.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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
    public LiveData<List<User>> getAllUsers(){
        return  mDataSource.getUserLiveData();
    }
    public void newUser(String userName, String userSchool, String userPlace){ mDataSource.newUser(userName,userSchool,userPlace); }
    public void updateUser(User user){
        mDataSource.updateUser(user);
    }
    public void deleteUser(User user){  mDataSource.deleteUser(user); }
    public void clear(){
        mDataSource.clear();
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
