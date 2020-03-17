package com.example.androidlabs2020w;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import static com.example.androidlabs2020w.myOpener.TABLE_NAME;


public class ChatRoomActivity extends AppCompatActivity {

    public static final String ChatRoomActivity = "CHATROOM_ACTIVITY";

    ArrayList<Message> MessageObj = new ArrayList<>();

    myAdapter myAdapter ;
    myOpener dbOpener;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbOpener = new myOpener(this);

        db = dbOpener.getWritableDatabase();

        String[] columns = {myOpener.COL_ID, myOpener.COL_MESSAGE};
        Cursor results = db.query(false, TABLE_NAME, columns, null, null, null, null, null, null);

        printCursor(results);

//        int msgColumnIndex = results.getColumnIndex(myOpener.COL_MESSAGE);
//        int sendColumnIndex = results.getColumnIndex(myOpener.COL_SEND);
//        int idColIndex = results.getColumnIndex(myOpener.COL_ID);
//
//        while(results.moveToNext())
//        {
//            String msg = results.getString(sendColumnIndex);
//            Integer send = results.getInt(msgColumnIndex);
//            long id = results.getLong(idColIndex);
//
//
//            //add the new Contact to the array list:
//            MessageObj.add(new Message(msg, send.equals(), id));
//        }

        setContentView(R.layout.activity_chat_room);

        ListView lv = (ListView) findViewById(R.id.theListView);

        EditText et = (EditText) findViewById(R.id.textMessageField);

        lv.setAdapter(myAdapter = new myAdapter());

        lv.setOnItemClickListener( (p, b, pos, id) -> {


            Message selectedMessage = MessageObj.get(pos);

//            View message_view = getLayoutInflater().inflate(R.id.textMessageField);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")


                    //What is the message:
                    .setMessage("The selected row" + pos + "\nThe database id is:" + id)


                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        Message m = MessageObj.get(pos);
                        deleteMessage(m);
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

            ContentValues newValue = new ContentValues();
            newValue.put(myOpener.COL_MESSAGE, et.getText().toString());
            newValue.put(myOpener.COL_SEND, 1);
            long newId = db.insert(TABLE_NAME, null, newValue);

            Message newMessage = new Message(et.getText().toString(), true, newId);
            et.setText("");

            MessageObj.add(newMessage);
            myAdapter.notifyDataSetChanged();
        });

        Button rb = (Button) findViewById(R.id.receiveButton);
        rb.setOnClickListener(v -> {

            ContentValues newValue = new ContentValues();
            newValue.put(myOpener.COL_MESSAGE, et.getText().toString());
            newValue.put(myOpener.COL_SEND, 0);
           long newId = db.insert(TABLE_NAME, null, newValue);

            Message newMessage = new Message(et.getText().toString(), false, newId);
            et.setText("");

            MessageObj.add(newMessage);
            myAdapter.notifyDataSetChanged();

        });


    }

protected void deleteMessage(Message c){
        db.delete(TABLE_NAME, myOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
}

    public class Message{
        long id;
        String msgtyped;

        public Message(String s, boolean b, long i){
            msgtyped = s;

            isSend = b;

            id = i;



        }

        public String getMsgtyped(){
            return msgtyped;
        }

        public boolean isSend(){
            return isSend;
        }

        public long getId(){return id;}


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

    public void printCursor(Cursor c){
        c = db.rawQuery("SELECT * from " + TABLE_NAME, null);
        int colIndex = c.getColumnIndex(myOpener.COL_MESSAGE);
        int msgIndex = c.getColumnIndex(myOpener.COL_SEND);


        for(int i = 0; i < c.getCount(); i++){
            c.moveToNext();
            String fn = c.getString(colIndex);
            Integer nn = c.getInt(msgIndex);
            Log.e("Message", "Message: " + fn + " Send: " + nn);

        }

        int columnNumber = c.getColumnCount();
        Log.e(ChatRoomActivity, "Number of columns: " + columnNumber);

        for(int i = 0; i < columnNumber; i++){
            Log.e(ChatRoomActivity, "Column[" + i + "] name: " + c.getColumnName(i));
        }


        int columnResults = c.getCount();
        Log.e(ChatRoomActivity, "Number of results in columns: " + columnResults);

        //each row of results?


    }}
