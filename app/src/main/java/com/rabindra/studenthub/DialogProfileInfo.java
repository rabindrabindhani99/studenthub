package com.rabindra.studenthub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogProfileInfo extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int IMAGE_PICKER_REQUEST_CODE = 1;
    final boolean permissionRequested = false;
    CircleImageView prevImage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userId;
    {
        assert user != null;
        userId = user.getUid();
    }

    byte[] compressedImageData;


    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + userId);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_profile_info);

        LinearLayout profileImage = findViewById(R.id.select_picture);
        prevImage = findViewById(R.id.profile_picture_preview);
        TextInputEditText nameEntry = findViewById(R.id.name_update_entry);
        TextInputEditText emailEntry =findViewById(R.id.email_update_entry);
        Button updateProfile = findViewById(R.id.update_profile);



        Intent intent =new Intent();
        String NameOfUser = intent.getStringExtra("UserName");

        String EmailOfUser = user.getEmail();

        if(NameOfUser!=null)
        {
            nameEntry.setText(NameOfUser);
            nameEntry.setEnabled(false);
        }
        else
        {
            nameEntry.setText("");
            nameEntry.setEnabled(true);
        }

        if(EmailOfUser!=null)
        {
            emailEntry.setText(EmailOfUser);
            emailEntry.setEnabled(false);
        }
        else
        {
            emailEntry.setText("");
            nameEntry.setEnabled(true);
        }

        //When upload Image button clicked to pick and compress the image
        profileImage.setOnClickListener(v -> {
            //Check if the app has storage permission
            if (ContextCompat.checkSelfPermission(DialogProfileInfo.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!permissionRequested) {
                    ActivityCompat.requestPermissions(DialogProfileInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    Toast.makeText(DialogProfileInfo.this, "Permission denied. Please grant permission in app settings", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Permission is already granted
                launchImagePicker();
            }
        });


        //When Update Profile button get clicked
        updateProfile.setOnClickListener(v ->
        {
            String updateName=nameEntry.getText().toString().trim();
            String updateEmail=emailEntry.getText().toString().trim();

            if(updateName.equals("") || updateName.length()<3)
            {
                nameEntry.setError("Put a valid name");
            }
            else
            {
                userRef.child("name").setValue(updateName);
            }

            if(updateEmail.equals(""))
            {
                emailEntry.setError("Put a valid E-mail");
            }
            else
            {
                userRef.child("email").setValue(updateEmail);
            }

            updateFirebaseUserProfilePhoto(compressedImageData);
            Intent MainIntent=new Intent(DialogProfileInfo.this,MainActivity.class);
            startActivity(MainIntent);

        });






    }





    /*============================================================================================================*/
    //Pick the image from user database
    private void launchImagePicker()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
    }

    // Handle the image picker result to get the image URI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the image URI from the data
            Uri imageUri = data.getData();

            // Get the bitmap from the URI and compress it
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int quality = 50;
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            while (baos.toByteArray().length / 1024 > 100) {
                baos.reset();
                quality -= 5;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            }

            // Get the compressed bitmap as a byte array
            compressedImageData = baos.toByteArray();

            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(compressedImageData, 0, compressedImageData.length);
            prevImage.setImageBitmap(compressedBitmap);

        }
    }

    /*=================================================================================================================*/

    private void updateFirebaseUserProfilePhoto(byte[] compressedImageData)
    {
        if (user != null) {
            StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("profile_photos").child(userId + ".jpg");
            UploadTask uploadTask = photoRef.putBytes(compressedImageData);
            uploadTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    photoRef.getDownloadUrl().addOnCompleteListener(downloadUrlTask -> {
                        if (downloadUrlTask.isSuccessful()) {
                            Uri downloadUrl = downloadUrlTask.getResult();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUrl)
                                    .build();
                            user.updateProfile(profileUpdates);
                        }
                    });
                }
            });
        }


    }


}