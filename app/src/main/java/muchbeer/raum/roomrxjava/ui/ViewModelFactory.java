package muchbeer.raum.roomrxjava.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import muchbeer.raum.roomrxjava.DataSource.UserDataSouce;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserDataSouce mDataSource;

    public ViewModelFactory(UserDataSouce mDataSource) {
        this.mDataSource = mDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
