package algonquin.cst2335.rustomlab01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Represents the main activity for a simple password checker application.
 * This activity allows users to enter a password and checks its complexity
 * based on predefined criteria including uppercase letters, lowercase letters,
 * numbers, and special characters.
 *
 * @author Rustom Trayvilla
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** TextView to diplay instruction to the user.*/
    private TextView tv;

    /** EditText field for the user to enter their password,*/
    private EditText et;

    /** Button that when clicked, trieggers the password complexity check*/
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);


        btn.setOnClickListener(v -> {
            String password = et.getText().toString();
            boolean isComplex = checkPasswordComplexity(password);
            if (isComplex) {
                //tv.setText(R.string.met); // "password meets the requirements"
            } else {
                //tv.setText(R.string.notMet); // Shall noit pass
            }
        });

    }

    /**
     * The purpose of this function is to checked
     * if the string has an Upper Case letter, a lower case letter,
     * a number, and a special symbol (#$%^&*!@?).
     *
     * @param password The string that is being checked
     */
    boolean checkPasswordComplexity(String password) {
        boolean foundUpperCase = !password.equals(password.toLowerCase());
        boolean foundLowerCase = !password.equals(password.toUpperCase());
        boolean foundNumber = password.matches(".*\\d.*");
        boolean foundSpecial = false;
        for(char c : password.toCharArray()) {
            if(isSpecialCharacter(c)) {
                foundSpecial = true;
                break;
            }
        }


        if (!foundUpperCase || !foundLowerCase || !foundNumber || !foundSpecial) {

            if (!foundUpperCase) {
                //showToast(R.string.hasUpperCase);
                return false;
            }
            if (!foundLowerCase) {
                //showToast(R.string.hasLowerCase);
                return false;
            }
            if (!foundNumber) {
                //showToast(R.string.hasNumber);
                return false;
            }
            if (!foundSpecial) {
               //showToast(R.string.hasSpecial);
                return false;
            }

            //showToast(R.string.met); // If all checks pass


        }
        return true;
    }





    /**
     * Determines if a character is one of the specified special characters.
     *
     * @param c The character to check.
     * @return true if the character is a special character; false otherwise.
     */
    private boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }

    /**
     * Displays a Toast message to the user.
     *
     * @param messageId The resource ID of the string to display.
     */
    private void showToast(int messageId) {
        Toast.makeText(this, getString(messageId), Toast.LENGTH_LONG).show();
    }
}




