package muchbeer.raum.roomrxjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import muchbeer.raum.roomrxjava.R;
import muchbeer.raum.roomrxjava.model.User;
import muchbeer.raum.roomrxjava.ui.MvvMRoomActivity;
import muchbeer.raum.roomrxjava.ui.UserActivity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> userList;
    private MvvMRoomActivity userActivity;


    public UserAdapter(Context context, ArrayList<User> userList, MvvMRoomActivity userActivity) {
        this.context = context;
        this.userList = userList;
        this.userActivity = userActivity;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_items,
                                        parent,
                                        false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        final User user = userList.get(position);

        holder.userName.setText(user.getUserName());
        holder.userPlace.setText(user.getUserPlace());
        holder.userSchool.setText(user.getUserSchool());

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                userActivity.addAndEditContacts(true, user, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, userSchool, userPlace;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.txtUsername);
            userSchool = itemView.findViewById(R.id.txtUserSchool);
            userPlace = itemView.findViewById(R.id.txtUserplace);
        }
    }
}
