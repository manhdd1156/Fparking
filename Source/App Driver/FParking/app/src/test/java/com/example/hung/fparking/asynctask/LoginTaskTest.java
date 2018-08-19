package com.example.hung.fparking.asynctask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import com.example.hung.fparking.BuildConfig;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.login.CustomToast;
import com.example.hung.fparking.login.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import com.example.hung.fparking.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginTaskTest {
    private MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
        Session.spref = mActivity.getSharedPreferences("intro", 0);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull( mActivity );
    }

    @Test
    public void testButton() {
        assertThat(mActivity.loginButton.getText().toString(), equalTo("ĐĂNG NHẬP"));
    }

    @Test
    public void testButtonClickNullData() throws Exception {
        Button view = (Button) mActivity.findViewById(R.id.loginBtn);
        EditText phone =  mActivity.findViewById(R.id.phone);
        EditText pass =  mActivity.findViewById(R.id.login_password);
        phone.setText("");
        pass.setText("");
        assertNotNull(view);
        view.performClick();
        ShadowActivity shadowActivity = shadowOf(mActivity);
        ShadowActivity.IntentForResult intentForResult = shadowActivity.getNextStartedActivityForResult();
        assertNull(intentForResult);
    }

    @Test
    public void testButtonClickWrongTypeData() throws Exception {
        Button view = (Button) mActivity.findViewById(R.id.loginBtn);
        EditText phone =  mActivity.findViewById(R.id.phone);
        EditText pass =  mActivity.findViewById(R.id.login_password);
        phone.setText("ffvcbbv");
        pass.setText("vb");
        assertNotNull(view);
        view.performClick();
        ShadowActivity shadowActivity = shadowOf(mActivity);
        ShadowActivity.IntentForResult intentForResult = shadowActivity.getNextStartedActivityForResult();
        assertNull(intentForResult);
    }

    @Test
    public void testButtonClickWrongData() throws Exception {
        Button view = (Button) mActivity.findViewById(R.id.loginBtn);
        EditText phone =  mActivity.findViewById(R.id.phone);
        EditText pass =  mActivity.findViewById(R.id.login_password);
        phone.setText("01288028666");
        pass.setText("1995");
        assertNotNull(view);
        view.performClick();
        ShadowActivity shadowActivity = shadowOf(mActivity);
        ShadowActivity.IntentForResult intentForResult = shadowActivity.getNextStartedActivityForResult();
        assertNull(intentForResult);
    }

    @Test
    public void testButtonClickCorrectData() throws Exception {
        Button view = (Button) mActivity.findViewById(R.id.loginBtn);
        EditText phone =  mActivity.findViewById(R.id.phone);
        EditText pass =  mActivity.findViewById(R.id.login_password);
        phone.setText("01288028666");
        pass.setText("123456");
        assertNotNull(view);
        view.performClick();
        ShadowActivity shadowActivity = shadowOf(mActivity);
        ShadowActivity.IntentForResult intentForResult = shadowActivity.getNextStartedActivityForResult();
        assertNotNull(intentForResult);
    }

}