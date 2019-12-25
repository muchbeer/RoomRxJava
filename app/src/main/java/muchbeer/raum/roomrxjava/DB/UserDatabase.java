package muchbeer.raum.roomrxjava.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import muchbeer.raum.roomrxjava.model.User;

@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "USer.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
