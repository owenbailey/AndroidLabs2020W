package com.example.androidlabs2020w;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {


    ArrayList<Message> MessageObj = new ArrayList<>();

    myAdapter myAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat_room);

        ListView lv = (ListView) findViewById(R.id.theListView);

        EditText et = (EditText) findViewById(R.id.textMessageField);

        lv.setAdapter(myAdapter = new myAdapter());

        lv.setOnItemClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row" + pos + "\nThe database id is:" + id)


                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        MessageObj.remove(pos );
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { });

                    //An optional third button:
                    //   .setNeutralButton("Maybe", (click, arg) -> {  })

                    //You can add extra layout elements:
                    //.setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    alertDialogBuilder.create().show();

        });

        Button sb = (Button) findViewById(R.id.sendButton);
        sb.setOnClickListener(v -> {
Message newmsg = new Message(et.getText().toString(), true);
            MessageObj.add(newmsg);
            myAdapter.notifyDataSetChanged();
            et.setText("");

        });

        Button rb = (Button) findViewById(R.id.receiveButton);
        rb.setOnClickListener(v -> {
            Message newmsg = new Message(et.getText().toString(), false);

            MessageObj.add(newmsg);
            myAdapter.notifyDataSetChanged();
            et.setText("");
        });


    }



    public class Message{


        String msgtyped;

        public Message(String s, boolean b){
            msgtyped = s;

            isSend = b;



        }

        public String getMsgtyped(){
            return msgtyped;
        }

        public boolean isSend(){
            return isSend;
        }



        boolean isSend;
    }

    private class myAdapter extends BaseAdapter {

        public int getCount() { return MessageObj.size();}

        public Message getItem(int position) { return  MessageObj.get(position); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            Message thisrow = getItem(position);
            //make a new row:
            View newView =null;
            if (thisrow.isSend())
              newView = inflater.inflate(R.layout.layout_send, parent, false);
            else
                newView = inflater.inflate(R.layout.layout_receive, parent, false);

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textMessageField);
            tView.setText( getItem(position).getMsgtyped() );

            //return it to be put in the table
            return newView;
        }
    }
}
