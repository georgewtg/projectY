package com.georgewiliam.project_y;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.georgewiliam.project_y.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostArrayAdapter extends ArrayAdapter<Post> {
    public PostArrayAdapter(@NonNull Context context, List<Post> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.post_view, parent, false);
        }

        Post currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired Views
        TextView username = currentItemView.findViewById(R.id.username_text);
        TextView email = currentItemView.findViewById(R.id.email_text);
        TextView timestamp = currentItemView.findViewById(R.id.timestamp_text);
        ImageView userView = currentItemView.findViewById(R.id.user_view);
        ImageView postView = currentItemView.findViewById(R.id.post_image_view);

        if (currentNumberPosition.post_img_id != null) {
            String userImage = "https://drive.google.com/uc?id=" + currentNumberPosition.profile_img_id;
            Picasso.get().load(userImage).into(userView);
        }

        username.setText(currentNumberPosition.username);
        email.setText(currentNumberPosition.email);
        timestamp.setText(currentNumberPosition.post_time.toString());

        String postImage = "https://drive.google.com/uc?id=" + currentNumberPosition.post_img_id;
        Picasso.get().load(postImage).into(postView);

        ConstraintLayout constraintLayout = currentItemView.findViewById(R.id.post_view_layout);
        constraintLayout.setOnClickListener(v -> {
            PostDetailActivity.selectedPost = currentNumberPosition;
            moveActivity(MainActivity.mContext, PostDetailActivity.class);
        });

        // then return the recyclable view
        return currentItemView;
    }

    private static void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(ctx, intent, null);
    }
}
