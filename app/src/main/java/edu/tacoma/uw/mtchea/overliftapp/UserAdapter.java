package edu.tacoma.uw.mtchea.overliftapp;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class that helps bridge objects between AdapterView and the data thats being passed
 * into the View.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private final Context context;
    private final List<UserSocial> userSocialList;

    /**
     * Receives the Social View
     * @param context
     * @param userSocialList
     */
    public UserAdapter(Context context, List<UserSocial> userSocialList) {
        this.context = context;
        this.userSocialList = userSocialList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserSocial user = userSocialList.get(position);
        holder.textViewEmail.setText(user.email);
    }

    @Override
    public int getItemCount() {
        return userSocialList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textViewEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewEmail = itemView.findViewById(R.id.textViewEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserSocial user = userSocialList.get(getAdapterPosition());
                    Intent intent = new Intent(context, SendNotificationActivity.class);
                    intent.putExtra("user", user);
                    context.startActivity(intent);
                }
            });

        }
    }
}
