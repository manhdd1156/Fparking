package com.example.hung.fparking.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
    private static Button loginButton,btnOK;
    private static TextView error;
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
        view = inflater.inflate(R.layout.activity_login, container, false);

        initViews();
        loginButton.setOnClickListener(this);
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
//            forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
//            signUp = (TextView) view.findViewById(R.id.createAccount);
//        show_hide_password = (CheckBox) view
//                .findViewById(R.id.show_hide_password);
//            loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

            // Load ShakeAnimation
//            shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
//                    R.anim.shake);

            // Setting text selector over textviews
//            @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
//
//            ColorStateList csl = ColorStateList.createFromXml(getResources(),
//                    xrp);

//            forgotPassword.setTextColor(csl);
//            show_hide_password.setTextColor(csl);
//            signUp.setTextColor(csl);
        } catch (Exception e) {
            System.out.println("lỗi ở login_fragment : " + e);
        }
    }

    // Set Listeners
    private void setListeners() {
//
//        forgotPassword.setOnClickListener(this);
//        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
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
//            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập số điện thoại");
        }
        if (getPassword.equals("") || getPassword.isEmpty()) {
//            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập mật khẩu");
        }
        if(getPassword.length()<6 || getPassword.length()>24) {
            showDialog("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự");
        }
        // Check if email id is valid or not
        else if (!m.find())
            showDialog("Số điện thoại hoặc mật khẩu không đúng");
            // Else do login and do your stuff
        else {
            new ManagerLoginTask("first_time",getPhone,getPassword, this);
//            mStaffLoginTask = new StaffLoginTask(getPhone, getPassword, this);
//            mStaffLoginTask.execute((Void) null);
//            Toast.makeText(getActivity(), "Vui lòng đợi xíu.", Toast.LENGTH_SHORT)
//                    .show();
        }
    }
    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        error = (TextView) mView.findViewById(R.id.tvAlert);
        btnOK = (Button) mView.findViewById(R.id.btnOK);
        error.setText(text);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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

