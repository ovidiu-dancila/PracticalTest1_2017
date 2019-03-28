package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Integer leftPress = 0;
    Integer rightPress = 0;
    int serviceStatus = Constants.STOPPED_SERVICE;
    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[MESSAGE]", intent.getStringExtra(Constants.DATA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        if (savedInstanceState != null) {
            EditText left_txt = findViewById(R.id.left_text);
            if(savedInstanceState.getString(Constants.LEFT_TEXT) != null) {
                left_txt.setText(savedInstanceState.getString(Constants.LEFT_TEXT));
            }
            EditText right_text = findViewById(R.id.right_text);
            if(savedInstanceState.getString(Constants.RIGHT_TEXT) != null) {
                right_text.setText(savedInstanceState.getString(Constants.RIGHT_TEXT));
            }
        }

        final Button left_button = findViewById(R.id.left_btn);
        final Button right_button = findViewById(R.id.right_btn);

        final EditText left_txt = findViewById(R.id.left_text);
        final EditText right_text = findViewById(R.id.right_text);

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer leftPress = Integer.parseInt(left_txt.getText().toString());
                left_txt.setText(String.valueOf(leftPress + 1));


                int left = Integer.parseInt(left_txt.getText().toString());
                int right = Integer.parseInt(right_text.getText().toString());

                if (left + right > Constants.MAX_CLICKS) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("firstNumber", left);
                    intent.putExtra("secondNumber", right);
                    getApplicationContext().startService(intent);

                    serviceStatus = Constants.STARTED_SERVICE;
                }
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer rightPress = Integer.parseInt(right_text.getText().toString());
                right_text.setText(String.valueOf(rightPress + 1));

                int left = Integer.parseInt(left_txt.getText().toString());
                int right = Integer.parseInt(right_text.getText().toString());

                if (left + right > Constants.MAX_CLICKS && serviceStatus == Constants.STOPPED_SERVICE) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("firstNumber", left);
                    intent.putExtra("secondNumber", right);
                    getApplicationContext().startService(intent);

                    serviceStatus = Constants.STARTED_SERVICE;
                }
            }
        });

        Button next_activity = findViewById(R.id.next_activity_btn);
        next_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String left = left_txt.getText().toString();
                String right = right_text.getText().toString();
                int result = Integer.parseInt(left) + Integer.parseInt(right);

                Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                intent.putExtra("nof_clicks", result);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });

        intentFilter.addAction(Constants.ACTION_TIME);
        intentFilter.addAction(Constants.ACTION_MEAN);
        intentFilter.addAction(Constants.ACTION_GEOM_MEAN);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case Constants.REQUEST_CODE:
                Toast.makeText(getApplicationContext(), "Result code: " + resultCode, Toast.LENGTH_LONG).show();
                break;

            // process other request codes
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText left_txt = findViewById(R.id.left_text);
        if(savedInstanceState.getString(Constants.LEFT_TEXT) != null) {
            left_txt.setText(savedInstanceState.getString(Constants.LEFT_TEXT));
        }
        EditText right_text = findViewById(R.id.right_text);
        if(savedInstanceState.getString(Constants.RIGHT_TEXT) != null) {
            right_text.setText(savedInstanceState.getString(Constants.RIGHT_TEXT));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText left_txt = findViewById(R.id.left_text);
        outState.putString(Constants.LEFT_TEXT, left_txt.getText().toString());

        EditText right_text = findViewById(R.id.right_text);
        outState.putString(Constants.RIGHT_TEXT, right_text.getText().toString());
    }
}
