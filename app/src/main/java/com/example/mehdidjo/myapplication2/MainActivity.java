package com.example.mehdidjo.myapplication2;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.mehdidjo.myapplication2.model.User;
import com.example.mehdidjo.myapplication2.model.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int RC_PHOTO_PICKER=2;
    MessagesListAdapter<Message> adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Message");

    String IMAGE_URL="https://hsto.org/getpro/habr/post_images/e4b/067/b17/e4b067b17a3e414083f7420351db272b.jpg";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String selectedPath = getPath(selectedImageUri);
            //IMAGE_URL=selectedPath;
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

        User user = new User("sqdqd" , "mehdi");
        Message message = new Message("hello" , "abcd" , user);
        adapter.addToStart( message, true);


        MessageInput inputView = (MessageInput) findViewById(R.id.input);

        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message

                User user = new User("sqdaqd" , "mehdi");
                Message message = new Message(input.toString(), "dabacd", user);
                adapter.addToStart(message, true);
                myRef.push().setValue(input.toString());
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

                User user = new User("sqdaqd" , "mehdi");
                Message message = new Message(null, "qkjsndj", null);
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
