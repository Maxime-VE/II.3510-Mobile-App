package fr.isep.hello;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Button
    Button EditButton;
    Button AddFontButton;
    Button MinusFontButton;
    Button ColorButton;

    int currentColor = -1; //Init color at first item of the list
    /** Color element for android are int defined by ARGB code 'Alpha, Red, Green, Blue' */
    int[] colorList = {
        android.graphics.Color.RED,        //   0xFFFF0000
        android.graphics.Color.GREEN,      //   0xFF00FF00
        android.graphics.Color.BLUE,       //   0xFF0000FF
        android.graphics.Color.YELLOW,     //   0xFFFFFF00
        android.graphics.Color.CYAN,       //   0xFF00FFFF
        android.graphics.Color.MAGENTA,    //   0xFFFF00FF
        android.graphics.Color.GRAY,       //   0xFF808080
        android.graphics.Color.BLACK,      //   0xFF000000
        android.graphics.Color.WHITE       //   0xFFFFFFFF
    };

    //Text
    EditText EditTextField;
    TextView TextField;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        EditButton = findViewById(R.id.edit_text_button);
        AddFontButton = findViewById(R.id.increase_font_button);
        MinusFontButton = findViewById(R.id.decrease_font_button);
        ColorButton = findViewById(R.id.change_color_button);


        EditTextField = findViewById(R.id.edit_text_field);
        TextField = findViewById(R.id.edit_text);

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextField.setText(EditTextField.getText());

            }
        });

        AddFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextField.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, TextField.getTextSize() + 5); // Value "android.util.TypedValue.COMPLEX_UNIT_PX"
                                                                                                                 //  allow a definition of size in pixels (PX)
            }
        });

        MinusFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextField.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, TextField.getTextSize() - 5);

            }
        });

        ColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newColor;
                if (currentColor != colorList.length - 1) {
                    currentColor += 1;
                } else {
                    currentColor = 0;
                }
                newColor = colorList[currentColor];
                TextField.setTextColor(newColor);
            }
        });

    }


}
