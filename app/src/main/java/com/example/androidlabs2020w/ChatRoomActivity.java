package com.example.androidlabs2020w;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {


    ArrayList<Message> MessageObj = new ArrayList<>();

    ListAdapter myAdapter = new ListAdapter() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button sb = (Button) findViewById(R.id.sendButton);
        sb.setOnClickListener(v -> {

            elements.add("Hi");
            myAdapter.notifyDataSetChanged();


        });

        Button rb = (Button) findViewById(R.id.receiveButton);
        rb.setOnClickListener(v -> {


        });


    }



    public class Message{


    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return MessageObj.size();}

        public Object getItem(int position) { return "This is row " + position; }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout., parent, false);

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }
    }
}
