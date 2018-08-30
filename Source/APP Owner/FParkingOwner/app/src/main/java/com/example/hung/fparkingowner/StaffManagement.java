package com.example.hung.fparkingowner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hung.fparkingowner.asynctask.GetCityTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.asynctask.ManagerStaffTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.dto.StaffDTO;
import com.example.hung.fparkingowner.model.CheckNetwork;
import com.example.hung.fparkingowner.profile.ProfileActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffManagement extends AppCompatActivity implements IAsyncTaskHandler {
    ImageView backStaff, addStaff;
    TextView tvSlistName, tvSlistPhone;
    EditText tbAddStaffName, tbAddStaffPhone, tbAddStaffAddress, tbAddStaffPass;
    Button btnConfirmAddStaff, btnOK;
    Spinner sprinerParking;
    TextView error;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog dialog;
    ArrayList<Integer> idParkinglist = new ArrayList<>();
    ArrayList<String> dropdownList = new ArrayList<>();
    int parkingidSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);
        // lấy thông tin các bãi xe và fill vào dropdownlist
//        new ManagerParkingTask("getbyowner", null, new IAsyncTaskHandler() {
//            @Override
//            public void onPostExecute(Object o) {
//                final ArrayList<listParkingDTO> plist;
//                if (o instanceof List) {
//                    plist = (ArrayList<listParkingDTO>) o;
//                    for (int i = 0; i < plist.size(); i++) {
//                        idParkinglist.add(plist.get(i).getId());
//                        dropdownList.add(plist.get(i).getAddress());
//                    }
//
//                }
//            }
//        });

        Session.homeActivity = this;
        backStaff = findViewById(R.id.imageViewBackStaff);
        addStaff = findViewById(R.id.imageViewAddStaff);
        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(StaffManagement.this);
                final View mView = getLayoutInflater().inflate(R.layout.activity_dialog_add_staff, null);
                mBuilder.setView(mView);

                dialog = mBuilder.create();
                sprinerParking = (Spinner) mView.findViewById(R.id.spinner3);
                tbAddStaffName = mView.findViewById(R.id.tbNameAddStaff);
                tbAddStaffPhone = mView.findViewById(R.id.tbPhoneAddStaff);
                tbAddStaffAddress = mView.findViewById(R.id.tbAddressAddStaff);
                tbAddStaffPass = mView.findViewById(R.id.tbPhoneAddStaff);
                btnConfirmAddStaff = mView.findViewById(R.id.btnAddStaff);
                btnConfirmAddStaff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pattern p = Pattern.compile(Constants.regEx);
                        String phone = tbAddStaffPhone.getText().toString();
                        if (tbAddStaffPhone.getText().toString().contains("+84")) {
                            phone = tbAddStaffPhone.getText().toString().replace("+84", "0");
                        }
                        Matcher m = p.matcher(phone);
                        if (tbAddStaffName.getText().toString().isEmpty() || tbAddStaffName.getText().toString().equals("")) {
                            showDialog("Hãy nhập tên ", 0);
                        } else if (tbAddStaffPhone.getText().toString().isEmpty() || tbAddStaffPhone.getText().toString().equals("")) {
                            showDialog("Hãy nhập số điện thoại", 0);
                        } else if (tbAddStaffAddress.getText().toString().isEmpty() || tbAddStaffAddress.getText().toString().equals("")) {
                            showDialog("Hãy nhập địa chỉ", 0);
                        } else if (tbAddStaffPass.getText().toString().isEmpty() || tbAddStaffPass.getText().toString().equals("")) {
                            showDialog("Hãy nhập mật khẩu", 0);
                        } else if (!tbAddStaffPhone.getText().toString().isEmpty() && !m.find()) {
                            showDialog("Số điện thoại không đúng định dạng, vui lòng nhập lại", 0);
                        } else if (tbAddStaffPass.getText().toString().length() < 6 || tbAddStaffPass.getText().toString().length() > 24) {
                            showDialog("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự", 0);
                        } else {

                            StaffDTO staffDTO = new StaffDTO();
                            staffDTO.setName(tbAddStaffName.getText().toString());
                            staffDTO.setAddress(tbAddStaffAddress.getText().toString());
                            staffDTO.setPhone(tbAddStaffPhone.getText().toString());
                            staffDTO.setPass(tbAddStaffPass.getText().toString());
                            if (staffDTO.getPhone().contains("+84")) {
                                staffDTO.setPhone(staffDTO.getPhone().replace("+84", "0"));
                            }
                            staffDTO.setParking_id(parkingidSelected);
                            new ManagerStaffTask("create", staffDTO, new IAsyncTaskHandler() {
                                @Override
                                public void onPostExecute(Object o) {
                                    if ((boolean) o) {
                                        dialog.cancel();
                                        showDialog("Tạo thành công", 1);
                                    } else {
                                        dialog.cancel();
                                        showDialog("Tạo không thành công", 1);
                                    }
                                }
                            });


                        }
                    }
                });

                    new ManagerParkingTask("getbyowner", null, null, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            final ArrayList<ParkingDTO> plist;

                            if (o instanceof List) {
                                try {
                                plist = (ArrayList<ParkingDTO>) o;

                                for (int i = 0; i < plist.size(); i++) {
                                    idParkinglist.add(plist.get(i).getId());
                                    dropdownList.add(plist.get(i).getAddress());
                                }
                                parkingidSelected = idParkinglist.get(0);
                                ArrayAdapter<String> adapter = new ArrayAdapter(mView.getContext(), android.R.layout.simple_spinner_item, dropdownList);
                                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                sprinerParking.setAdapter(adapter);
                                sprinerParking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        parkingidSelected = idParkinglist.get(sprinerParking.getSelectedItemPosition());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                    dialog.show();
                                }catch (Exception e) {
                                    showDialog("Hãy tạo bãi xe trước",0);
                                }
                            }


                        }
                    });



            }
        });
        backStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSlistName = (TextView) findViewById(R.id.tvSListName);
        tvSlistPhone = (TextView) findViewById(R.id.tvSListPhone);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mRecyclerView = (RecyclerView) findViewById(R.id.staff_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new ManagerStaffTask("getbyowner", null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void showDialog(String text, final int type) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StaffManagement.this);
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
                if (type == 1) {
                    recreate();
                }
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(StaffManagement.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
                System.out.println("đã có mạng");
//                recreate();
            }
        }
    };

    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
        final ArrayList<StaffDTO> slist;
        try {
            if (o instanceof List) {
                slist = (ArrayList<StaffDTO>) o;
                mAdapter = new StaffRecyclerViewAdapter(slist);
                mRecyclerView.setAdapter(mAdapter);
                ((StaffRecyclerViewAdapter) mAdapter).setOnItemClickListener(new StaffRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(final int position, View v) {
                        System.out.println("view = " + v.getClass().getName());

                        // ... tapped on the item container (row), so do something different
                        Intent intentDetail = new Intent(StaffManagement.this, DetailStaffActivity.class);
                        finish();
                        intentDetail.putExtra("staffid", slist.get(position).getId() + "");
                        startActivity(intentDetail);


                    }

                });

            } else if (o instanceof String) {

            } else if (o instanceof Boolean) {

            }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở StaffManagerActivity : " + e);
        }
    }


}

class StaffRecyclerViewAdapter extends RecyclerView
        .Adapter<StaffRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "StaffRecyclerViewAdapter";
    private ArrayList<StaffDTO> mDataset;
    private static MyClickListener myClickListener;
    AlertDialog dialog;
    EditText confirmPasssOwner;
    Button btnConfirmPassOwner, btnOK;
    TextView tvError, error;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView name;
        TextView phone;
        ImageView imgCancel;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvSListName);
            phone = (TextView) itemView.findViewById(R.id.tvSListPhone);
            imgCancel = (ImageView) itemView.findViewById(R.id.imgSListCancel);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public StaffRecyclerViewAdapter(ArrayList<StaffDTO> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.phone.setText(mDataset.get(position).getPhone() + "");
        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("============================");
                // insert code here - there you have the current child
                showDialogConfirmPass(mDataset.get(position).getId());
            }
        });
    }

    public void showDialogConfirmPass(final int staffID) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Session.homeActivity);
        View mView = Session.homeActivity.getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        tvError = (TextView) mView.findViewById(R.id.tvError);
        confirmPasssOwner = (EditText) mView.findViewById(R.id.tbPassword);
        btnConfirmPassOwner = (Button) mView.findViewById(R.id.btnConfirm);
        btnConfirmPassOwner.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            String passMD5 = getMD5Hex(confirmPasssOwner.getText().toString());
                            if (confirmPasssOwner.getText().toString().isEmpty() || confirmPasssOwner.getText().toString().equals("")) {
                                tvError.setText("Hãy nhập mật khẩu");
                                tvError.setVisibility(View.VISIBLE);
                            } else if (!passMD5.equals(Session.currentOwner.getPass())) {
                                tvError.setText("Mật khẩu không đúng, vui lòng nhập lại");
                                tvError.setVisibility(View.VISIBLE);
                            } else {
                                StaffDTO stemp = new StaffDTO();
                                stemp.setId(staffID);
                                new ManagerStaffTask("delete", stemp, new IAsyncTaskHandler() {
                                    @Override
                                    public void onPostExecute(Object o) {
                    if((boolean) o) {
                        showDialog("Xóa thành công");
                    }else {
                        showDialog("Xóa không thành công");
                    }
                                        dialog.cancel();


                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println("Lỗi ProfileActivity/checkValidation : " + e);
                        }
                    }
                });

    }

    public void showDialog(String text) {
        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(Session.homeActivity);
        View mView = Session.homeActivity.getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
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
                Session.homeActivity.recreate();
            }
        });
    }

    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public void addItem(StaffDTO dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}