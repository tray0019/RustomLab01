package algonquin.cst2335.rustomlab01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private Button callButton;
    private ImageView profileImage;
    private ActivityResultLauncher<Intent> cameraResultLauncher;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize views
        phoneNumberEditText = findViewById(R.id.editTextPhone);
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        callButton = findViewById(R.id.callButton);
        profileImage = findViewById(R.id.profileImage);
        Button takePictureButton = findViewById(R.id.takePictureButton);
        // Check if the profile picture file exists
        String filename = "Picture.png"; // The name of the file where the picture is saved
        File file = new File(getFilesDir(), filename);

        // Load the saved phone number, if it exists
        String phoneNumber = prefs.getString("PhoneNumber", "");
        phoneNumberEditText.setText(phoneNumber);


        takePictureButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                cameraResultLauncher.launch(takePictureIntent);
            } else {
                Log.e("SecondActivity", "Unable to open camera");
            }
        });


        if (file.exists()) {
            // If the file exists, decode it into a Bitmap
            Bitmap theImage = BitmapFactory.decodeFile(filename);
            // Set the Bitmap as the source of the ImageView
            profileImage.setImageBitmap(theImage);
        }
        // Retrieve the intent that started this activity
        Intent fromPrevious = getIntent();

        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome back " + emailAddress);


        phoneNumberEditText = findViewById(R.id.editTextPhone); // Make sure you have this EditText in your XML
        callButton = findViewById(R.id.callButton); // And this button

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;
                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();

                            }
                            catch (FileNotFoundException e)
                            { e.printStackTrace();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }

                });
        cameraResultLauncher.launch(cameraIntent);



    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the phone number when the activity goes into the background
        String phoneNumber = phoneNumberEditText.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneNumber);
        editor.apply();
    }


    private void makePhoneCall() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        if (!phoneNumber.isEmpty()) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            } else {
                Log.e("SecondActivity", "No app found to make a phone call");
            }
        } else {
            Toast.makeText(SecondActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
        }
    }
}