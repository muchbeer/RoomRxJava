package muchbeer.raum.roomrxjava.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.roomrxjava.model.User;
import muchbeer.raum.roomrxjava.repository.UserRepository;

public class AltUserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    public AltUserViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getAllUsers(){
        return  userRepository.getUserLiveData();
    }

    public void newUser(String userName, String userSchool, String userPlace){
      userRepository.newUser(userName,userSchool,userPlace); }
    public void updateUser(User user){
        userRepository.updateUser(user);
    }
    public void deleteUser(User user){  userRepository.deleteUser(user); }

    public void clear(){
        userRepository.clear();
    }
}
