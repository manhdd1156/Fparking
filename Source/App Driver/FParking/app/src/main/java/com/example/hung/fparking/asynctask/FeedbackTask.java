package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;

import org.json.JSONObject;

public class FeedbackTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    String content;

    public FeedbackTask(String content, IAsyncTaskHandler container) {
        this.container = container;
        this.content = content;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("content", content);

            String json = httpHandler.requestMethod(Constants.API_URL + "drivers/feedbacks", formData.toString(), "POST");
            Log.e("Feedback: ", json);
            return true;
        } catch (Exception ex) {
            Log.e("Error CreateBooking:", ex.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean, "feedback");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        container.onPostExecute(false, "feedback");
    }
}

