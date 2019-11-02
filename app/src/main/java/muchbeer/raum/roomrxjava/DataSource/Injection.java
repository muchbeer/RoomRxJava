package muchbeer.raum.roomrxjava.DataSource;

import android.content.Context;

import muchbeer.raum.roomrxjava.DB.UserDatabase;
import muchbeer.raum.roomrxjava.ui.ViewModelFactory;

public class Injection {

    public static UserDataSouce provideUserDataSource(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSouce dataSource = provideUserDataSource(context);

        return new ViewModelFactory(dataSource);
    }
}
