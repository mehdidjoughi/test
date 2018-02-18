package com.example.mehdidjo.myapplication2;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.mehdidjo.myapplication2.model.Author;
import com.example.mehdidjo.myapplication2.model.Message;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int RC_PHOTO_PICKER=2;
    MessagesListAdapter<Message> adapter;

    String IMAGE_URL;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String selectedPath = getPath(selectedImageUri);
            IMAGE_URL=selectedPath;
            Log.v("Image test ","---> "+selectedImageUri.toString());
            Log.v("Image test ","---> "+selectedPath);
        }
    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessagesList messagesList = (MessagesList) findViewById(R.id.messagesList) ;

        adapter = new MessagesListAdapter<>("dfsdf", null);
        messagesList.setAdapter(adapter);

        Author author = new Author("sqdqd" , "mehdi");
        Message message = new Message("hello" , "abcd" , author);
        adapter.addToStart( message, true);


        MessageInput inputView = (MessageInput) findViewById(R.id.input);

        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message

                Author author = new Author("sqdaqd" , "mehdi");
                Message message = new Message(input.toString(), "dabacd",author);
                adapter.addToStart(message, true);
                return true;
            }
        });



        
        

        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                //select attachments
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY ,true);
                startActivityForResult(Intent.createChooser(intent,"Complet action using " ),RC_PHOTO_PICKER);

                Message message = new Message("qsjds", "qkjsndj", null);
                message.setImage(new Message.Image(IMAGE_URL));
                adapter.addToStart(message, true);


            }
        });


        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MainActivity.this).load(url).into(imageView);
            }
        };

    }
}
