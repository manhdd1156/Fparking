package com.example.hung.fparking.other;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.FeedbackTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.login.SignUp_Fragment;

public class Feedback extends AppCompatActivity implements IAsyncTaskHandler {
    ImageView backFeedback;
    Button buttonSendFeedback, btnOK;
    EditText editTextFeedback;
    AlertDialog dialog;
    TextView textViewAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Feedback.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        textViewAlert = mView.findViewById(R.id.textViewAlert);
        btnOK = mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        backFeedback = findViewById(R.id.imageViewBackFeedback);
        backFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFeedback = new Intent(Feedback.this, HomeActivity.class);
                startActivity(intentFeedback);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        editTextFeedback = findViewById(R.id.editTextFeedback);

        buttonSendFeedback = findViewById(R.id.buttonSendFeedback);
        buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextFeedback.getText().toString();
                FeedbackTask feedbackTask = new FeedbackTask(content, Feedback.this);
                feedbackTask.execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (Boolean.TRUE.equals(o)) {
            textViewAlert.setText("Gửi phản hồi thành công!");
            dialog.show();
        } else {
            textViewAlert.setText("Không kết nối được đến máy chủ!");
            dialog.show();
        }
    }
}
