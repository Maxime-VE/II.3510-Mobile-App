package fr.isep.hello;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.isep.hello.R;

public class MainActivity extends AppCompatActivity {

    Button AddButton;
    EditText EditText;
    TextView TextField;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        AddButton = findViewById(R.id.edit_text_button);
        EditText = findViewById(R.id.edit_text);
        TextField = findViewById(R.id.edit_text_field);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextField.setText(EditText.getText());

            }
        });

    }


}
