package ise1005.edu.fpt.vn.myrestaurant.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.UserLoginTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.cooker.CookerActivity;
import ise1005.edu.fpt.vn.myrestaurant.manager.Dashboard;
import ise1005.edu.fpt.vn.myrestaurant.notification.Notification;
import ise1005.edu.fpt.vn.myrestaurant.staff.TableActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements IAsyncTaskHandler {
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("onLoginActivityCreate", Session.currentUser != null ? Session.currentUser.toString() : "NULL");
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onPostExecute(Object o) {
        showProgress(false);
        mAuthTask = null;
        if (Boolean.TRUE.equals(o)) {
//            Notification notification= new Notification();
//            notification.setEvent("eventName");
//            Intent intent = new Intent(getBaseContext(), Notification.class);
//            Bundle extras = new Bundle();
//            extras.putString("eventName", "test");
//            intent.putExtras(extras);
//            startService(intent);
//            startService(new Intent(getBaseContext(), Notification.class));
//            Log.i("onPostExecute", Session.currentUser.toString());
//            Intent intent = new Intent(this, NotificationService.class);
//            startService(intent);
            if(Session.currentUser.getStatus() == 0){
                Intent intent = new Intent(getBaseContext(), Notification.class);
                Bundle extras = new Bundle();
                if(Session.currentUser.getRole_id()==1){
                    startActivity( new Intent(this, Dashboard.class));
                    extras.putString("eventName", Constants.PUSHER_EVENT_FOR_MANAGER);
                }else if(Session.currentUser.getRole_id()==3){
                    startActivity( new Intent(this, TableActivity.class));
                    extras.putString("eventName", Constants.PUSHER_EVENT_FOR_STAFF);
                }else{
                    startActivity( new Intent(this, CookerActivity.class));
                    extras.putString("eventName", Constants.PUSHER_EVENT_FOR_COOKER);
                }
                intent.putExtras(extras);
                startService(intent);
            }else{
                mPasswordView.setError(getString(R.string.error_account_disabled));
                mPasswordView.requestFocus();
            }

        } else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }
}

