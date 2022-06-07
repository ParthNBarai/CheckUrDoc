package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ProfActivity extends AppCompatActivity {
    private TextView text;
    private RadioGroup radioGroup;
    private RadioButton DoctorButton,PatientButton;
    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        radioGroup = findViewById(R.id.radioGroup);
        DoctorButton = findViewById(R.id.DoctorButton);
        PatientButton = findViewById(R.id.PatientButton);

        continueBtn = findViewById(R.id.continueBtn);



//        if(DoctorButton.isChecked() == true){
//            prompt=0;
//        } else if (PatientButton.isChecked() == true) {
//            prompt = 1;
//        }
//        else{
//            prompt = 2;
//        }




        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prompt=-1;

                int select = radioGroup.getCheckedRadioButtonId();

                if(select == R.id.DoctorButton){
                    prompt=0;
                }
                else if(select == R.id.PatientButton){
                    prompt=1;
                }


                String x = Integer.toString(prompt);
                Log.d("prompt",x);
                Intent intent = new Intent(ProfActivity.this, LoginScreen.class);
                Bundle extras = new Bundle();
                extras.putString("StringVariableName", prompt + "");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

}