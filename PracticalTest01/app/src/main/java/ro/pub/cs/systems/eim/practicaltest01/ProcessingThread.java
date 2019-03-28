package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context;
    int firstNumber;
    int secondNumber;
    boolean isRunning;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        isRunning = true;
        this.context = context;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    @Override
    public void run() {
        while(isRunning) {
            Log.d("PracticalTestTAG", "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
            sleep();

            int message_type = new Random().nextInt() % 3 + 1;
            float mean = (float)(firstNumber + secondNumber) / 2;
            float geom_mean = (float) Math.sqrt(firstNumber * secondNumber);
            String message = new Timestamp(System.currentTimeMillis()).toString() + " " + mean + " " + geom_mean;
            switch (message_type) {
                case Constants.MESSAGE_TIME:
                    sendMessage(message_type, message);
                    break;
                case Constants.MESSAGE_MEAN:
                    sendMessage(message_type, message);
                    break;
                case Constants.MESSAGE_GEOM_MEAN:

                    sendMessage(message_type, message);
                    break;
            }
        }

    }

    private void sleep() {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage(int messageType, String message) {
        Intent intent = new Intent();
        switch (messageType) {
            case Constants.MESSAGE_TIME:
                intent.setAction(Constants.ACTION_TIME);
                intent.putExtra(Constants.DATA, message);
                break;
            case Constants.MESSAGE_MEAN:
                intent.setAction(Constants.ACTION_MEAN);
                intent.putExtra(Constants.DATA, message);
                break;
            case Constants.MESSAGE_GEOM_MEAN:
                intent.setAction(Constants.ACTION_GEOM_MEAN);
                intent.putExtra(Constants.DATA, message);
                break;
        }
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}