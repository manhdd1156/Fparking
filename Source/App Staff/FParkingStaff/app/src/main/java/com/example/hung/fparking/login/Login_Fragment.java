package com.example.hung.fparking.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.notification.Notification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements OnClickListener, IAsyncTaskHandler {
    private static View view;

    private static EditText phoneNumber, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    //    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private FragmentManager fragmentManager;

//    private StaffLoginTask mStaffLoginTask = null;
    private SharedPreferences spref;
    private Login_Fragment login_Fragment;
    private SharedPreferences.Editor editor;
//    private String getPhone;
//    private String getPassword;
    public Login_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_login, container, false);

        initViews();
        setListeners();
        return view;
    }


    // Initiate Views
    private void initViews() {
        try {
            fragmentManager = getActivity().getSupportFragmentManager();
            spref = getActivity().getSharedPreferences("info",0);
            editor = spref.edit();
            phoneNumber = (EditText) view.findViewById(R.id.phone);
            password = (EditText) view.findViewById(R.id.login_password);
            loginButton = (Button) view.findViewById(R.id.loginBtn);
            forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
            signUp = (TextView) view.findViewById(R.id.createAccount);
//        show_hide_password = (CheckBox) view
//                .findViewById(R.id.show_hide_password);
            loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

            // Load ShakeAnimation
            shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.shake);

            // Setting text selector over textviews
            @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);

            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
//            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
            System.out.println("lỗi ở login_fragment : " + e);
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Constants.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Constants.SignUp_Fragment).commit();
                break;
        }
    }

    // Check Validation before login
    private void checkValidation() {
        // Get phone and password
        String getPhone = phoneNumber.getText().toString();
        String getPassword = password.getText().toString();
        if(getPhone.contains("+84")) {
            getPhone = getPhone.replace("+84","0");
        }
        // Check patter for email id
        Pattern p = Pattern.compile(Constants.regEx);

        Matcher m = p.matcher(getPhone);

        // Check for both field is empty or not
        if (getPhone.equals("") || getPhone.isEmpty()) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Hãy nhập số điện thoại");
        }
        if (getPassword.equals("") || getPassword.isEmpty()) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Hãy nhập mật khẩu");
        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Số điện thoại không đúng");
            // Else do login and do your stuff
        else {
            new ManagerLoginTask("first_time",getPhone,getPassword, this);
//            mStaffLoginTask = new StaffLoginTask(getPhone, getPassword, this);
//            mStaffLoginTask.execute((Void) null);
//            Toast.makeText(getActivity(), "Vui lòng đợi xíu.", Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    @Override
    public void onPostExecute(Object o) {
        if (Boolean.TRUE.equals(o)) {
            Intent intent = new Intent(view.getContext(), Notification.class);
            System.out.println("đăng nhập thành công");
            startActivity( new Intent(this.getContext(), HomeActivity.class));
            getActivity().finish();
            getActivity().startService(intent);

        } else {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Số điện thoại hoặc mật khẩu không đúng");
        }
    }
}

