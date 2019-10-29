package com.hautea.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    DbHelper db;
    ListView lvUsers;
    ArrayList<HashMap<String,String>> all_users;
    ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DbHelper(this);
        lvUsers = findViewById(R.id.lvusers);
        fetch_user();
    }

    private void fetch_user() {
        all_users = db.getAllUsers();
        adapter = new ListViewAdapter(this, R.layout.adapter_user, all_users);
        lvUsers.setAdapter(adapter);
    }

    private class ListViewAdapter extends ArrayAdapter {

        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> all_users;
        TextView tvfullname, tvusername;
        ImageView ivedit, ivdelete;

        public ListViewAdapter(Context context, int resource, ArrayList<HashMap<String, String>> all_users) {
            super(context, resource, all_users);
            inflater = LayoutInflater.from(context);
            this.all_users = all_users;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.adapter_user, parent,false);
            tvfullname = convertView.findViewById(R.id.tvfullname);
            tvusername = convertView.findViewById(R.id.tvusername);
            ivedit = convertView.findViewById(R.id.ivedit);
            ivdelete = convertView.findViewById(R.id.ivdelete);

            tvfullname.setText(all_users.get(position).get(db.TBL_USER_FULLNAME));
            tvusername.setText(all_users.get(position).get(db.TBL_USER_USERNAME));

            final int userid = Integer.parseInt(all_users.get(position).get(db.TBL_USER_ID));
            ivdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteUser(userid);
                    Toast.makeText(HomeActivity.this, "USER SUCCESSFULLY DELETED", Toast.LENGTH_SHORT).show();  
                    fetch_user();
                }
            });
            return (convertView);
        }
    }
}
