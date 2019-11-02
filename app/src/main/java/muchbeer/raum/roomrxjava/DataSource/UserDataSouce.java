package muchbeer.raum.roomrxjava.DataSource;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.model.User;

public interface UserDataSouce {

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    Flowable<User> getUser();

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param user the user to be inserted or updated.
     */
    Completable insertOrUpdateUser(User user);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllUsers();
}
