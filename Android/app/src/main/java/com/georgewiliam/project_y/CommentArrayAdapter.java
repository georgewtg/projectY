package com.georgewiliam.project_y;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.georgewiliam.project_y.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    public CommentArrayAdapter(@NonNull Context context, List<Comment> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.comment_view, parent, false);
        }

        Comment currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired Views
        TextView username = currentItemView.findViewById(R.id.username_comment);
        TextView email = currentItemView.findViewById(R.id.email_comment);
        ImageView userView = currentItemView.findViewById(R.id.user_comment_image);
        TextView commentText = currentItemView.findViewById(R.id.comment_text);

        if (currentNumberPosition.profile_img_id != null) {
            String userImage = "https://drive.google.com/uc?id=" + currentNumberPosition.profile_img_id;
            Picasso.get().load(userImage).into(userView);
        }

        username.setText(currentNumberPosition.username);
        email.setText(currentNumberPosition.email);
        commentText.setText(currentNumberPosition.comment);

        // then return the recyclable view
        return currentItemView;
    }
}
