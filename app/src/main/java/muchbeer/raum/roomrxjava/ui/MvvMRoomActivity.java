package muchbeer.raum.roomrxjava.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.roomrxjava.DB.UserDatabase;
import muchbeer.raum.roomrxjava.DataSource.Injection;
import muchbeer.raum.roomrxjava.R;
import muchbeer.raum.roomrxjava.adapter.UserAdapter;
import muchbeer.raum.roomrxjava.model.User;

public class MvvMRoomActivity extends AppCompatActivity {

    private static final String LOG_TAG = MvvMRoomActivity.class.getSimpleName();
    //private AltUserViewModel userViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private UserDatabase userAppDatabase;
    private UserAdapter userAdapter;
    private ArrayList<User> userList =  new ArrayList<>();
    private ViewModelFactory mViewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvv_mroom);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" School Logs ");

        mViewModelFactory = Injection.provideViewModelFactory(this);

        userViewModel = new ViewModelProvider(this, mViewModelFactory).get(UserViewModel.class);
//userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        recyclerView = findViewById(R.id.recycler_view_users);
        userAdapter = new UserAdapter(this, userList, MvvMRoomActivity.this);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {

    @Override
    public void onChanged(List<User> users) {
        userList.clear();
        userList.addAll(users);
        Log.d(LOG_TAG, "The vaLLUE is: "+ users);
        init();
        userAdapter.notifyDataSetChanged();
        }
});

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false, null, -1);
            }
        });

    }

    private void init() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);   }

    public void addAndEditContacts(final boolean isUpdate, final User user, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_user, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MvvMRoomActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView userTitle = view.findViewById(R.id.new_user_title);

        final EditText newUser = view.findViewById(R.id.edt_userName);
        final EditText newSchool = view.findViewById(R.id.edt_userSchool);
        final EditText newPlace = view.findViewById(R.id.edt_userPlace);

userTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && user != null) {
            newUser.setText(user.getUserName());
            newSchool.setText(user.getUserSchool());
            newPlace.setText(user.getUserPlace());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isUpdate) {

                    deleteUser(user, position);
                } else {

                    dialog.cancel();

                }
            }
        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(newUser.getText().toString())) {
                    Toast.makeText(MvvMRoomActivity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && user != null) {

                    updateUser(newUser.getText().toString(), newSchool.getText().toString(),
                            newPlace.getText().toString(), position);
                } else {

                    createUser(newUser.getText().toString(), newSchool.getText().toString(),
                            newPlace.getText().toString() );
               } }  });
    }

    private void deleteUser(final User user, int position) {
        userViewModel.deleteUser(user);
    }

    private void updateUser(final String userName, final String userSchool, final String userPlace, int position) {
        final User user = userList.get(position);

        user.setUserName(userName);
        user.setUserSchool(userSchool);
        user.setUserPlace(userPlace);

        userViewModel.updateUser(user);    }

    private void createUser(final String userName, final String userSchool, final String userPlace) {
        userViewModel.newUser(userName,userSchool,userPlace);  }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userViewModel.clear();
    }
}
