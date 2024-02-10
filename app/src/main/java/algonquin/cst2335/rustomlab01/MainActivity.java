package algonquin.cst2335.rustomlab01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
private static final String TAG = "MainActivity";
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailToSave = emailEditText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("LoginName", emailToSave);
                editor.putFloat("hi",4.5f);
                editor.putInt("Age",35);
                editor.apply();  // or editor.commit(); for synchronous saving

                Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
                nextPage.putExtra("EmailAddress", emailAddress);
                startActivity(nextPage);



            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "The application is now visible on screen.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "The application is now responding to user input\n" +
                "After these 3 function calls, your application is up and running on the screen. When another application comes up on screen and your application disappears beneath, there are other functions that get called:");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "The application is no longer visible.\n" +
                "Normally an application will always be asleep in the background when it is not visible on screen. It's only when an app is removed from the system by the user that the last function is called:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Any memory used by the application is freed.");
    }
}