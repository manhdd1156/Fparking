package com.example.hung.myapplication.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.config.Constants;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText phoneNumber;
    private static TextView submit;
    private static ImageView back;
    private static FragmentManager fragmentManager;

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        phoneNumber = (EditText) view.findViewById(R.id.phone);
        submit = (TextView) view.findViewById(R.id.nextBtn);
        back = (ImageView) view.findViewById(R.id.backToLoginBtn);


    }
    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new MainActivity().replaceLoginFragment();
                break;

            case R.id.nextBtn:

                // Call Submit button task
                submitButtonTask();
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPasswordOtp_Fragment(),
                                Constants.ForgotPasswordOtp_Fragment).commit();
                break;

        }

    }
    private void submitButtonTask() {
//        String getEmailId = emailId.getText().toString();
//
//        // Pattern for email id validation
//        Pattern p = Pattern.compile(Utils.regEx);
//
//        // Match the pattern
//        Matcher m = p.matcher(getEmailId);
//
//        // First check if email id is not null else show error toast
//        if (getEmailId.equals("") || getEmailId.length() == 0)
//
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Please enter your Email Id.");
//
//            // Check if email id is valid or not
//        else if (!m.find())
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Your Email Id is Invalid.");
//
//            // Else submit email id and fetch passwod or do your stuff
//        else
//            Toast.makeText(getActivity(), "Get Forgot Password.",
//                    Toast.LENGTH_SHORT).show();
    }
}

