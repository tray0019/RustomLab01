package algonquin.cst2335.rustomlab01.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;


import android.os.Bundle;
import android.widget.Toast;

import algonquin.cst2335.rustomlab01.R;
import algonquin.cst2335.rustomlab01.databinding.ActivityMainBinding;
import algonquin.cst2335.rustomlab01.ui.data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    //Created a variable per lab instruction
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainViewModel.class);

        //Deletes per Instruction
        //setContentView(R.layout.activity_main);

        //Added to directly reference functions from activity_main with their id
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //Deleted after added variable Binding
        //EditText myedit = findViewById(R.id.myedittext);
        //TextView myText = findViewById(R.id.textview);
        //Button btn = findViewById(R.id.myButton);

        //deleted per instruction
        /**
         variableBinding.myButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String editString = variableBinding.myedittext.getText().toString();
        variableBinding.textview.setText("Your edit text has: "+editString);
        }
        });
         */



        //Modify using Labda
        variableBinding.myButton.setOnClickListener(v -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, new Observer<String>(){
            @Override
            public void onChanged(String s) {
                String formattedText = getString(R.string.edit_text_message, model.editString.getValue());
                variableBinding.textview.setText(formattedText);
            }
        });

        variableBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        //Image for my activity main
        //variableBinding.imageV.setOnClickListener(v -> {

        //});

        //Image for my image button
        variableBinding.myImageButton.setOnClickListener(v -> {


            int width = v.getWidth();
            int height = v.getHeight();
            String toastMessage = getString(R.string.toast_message_size, width, height);
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
        });



        model.isSelected.observe(this, selected -> {


            variableBinding.radioButton.setChecked(selected);
            variableBinding.checkBox.setChecked(selected);
            variableBinding.switch1.setChecked(selected);

            String toastMessage = getString(R.string.value_now_message, selected.toString());
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

        });



    }

}