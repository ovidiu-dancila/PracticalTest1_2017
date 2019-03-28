package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);


        EditText txt = findViewById(R.id.edit_text);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras().containsKey("nof_clicks")) {
                int nofClicks = intent.getIntExtra("nof_clicks", -1);

                txt.setText(String.valueOf(nofClicks));

            }
        }

        Button okBtn = findViewById(R.id.ok_btn);
        Button cancelBtn = findViewById(R.id.cancel_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
