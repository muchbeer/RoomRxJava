package muchbeer.raum.roomrxjava.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import muchbeer.raum.roomrxjava.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    Flowable<List<User>> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUserMvvm(User user);

    @Delete
    public void deleteUserMvvm(User user);

    @Update
    public void updateUser(User user);

    @Query("DELETE FROM Users")
    void deleteAllUsers();
}
