package com.example.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.modle.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User>
{
    private Activity context;
    private int resource;
    private List<User> objects;

    private TextView txtUserID, txtPW,txtNameUser,txtRole;
    public UserAdapter(@NonNull Activity context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        User user = this.objects.get(position);
        addConrols(row);
        setUp(user);
        return row;
    }

    private void setUp(User user) {
        txtUserID.setText(user.getUserID());
        txtPW.setText(user.getPassWord());
        txtNameUser.setText(user.getName());
        txtRole.setText(user.getRole());
    }

    private void addConrols(View row) {
        txtUserID = (TextView)row.findViewById(R.id.txtUserID);
        txtPW = (TextView)row.findViewById(R.id.txtPW);
        txtNameUser = (TextView)row.findViewById(R.id.txtNameUser);
        txtRole = (TextView)row.findViewById(R.id.txtRole);
    }
}
