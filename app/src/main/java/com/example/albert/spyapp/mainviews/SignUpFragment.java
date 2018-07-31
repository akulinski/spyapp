package com.example.albert.spyapp.mainviews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.albert.spyapp.R;
import com.example.albert.spyapp.requests.ServerPost;
import com.example.albert.spyapp.utils.Urls;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends AppCompatActivity {

    private Button confirm;
    private Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private EditText login;
    private EditText email;
    private EditText password;
    private EditText repassword;
    private String passwd = "";
    private EditText debug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        confirm = findViewById(R.id.confirm_su);

        login = findViewById(R.id.login_su);
        email = findViewById(R.id.email_su);
        password = findViewById(R.id.password_su);
        repassword = findViewById(R.id.passwordagain_su);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkEmptyFields()) showDialog("Fill in all the fields",false);
                else if (checkpasswords()) {
                    hashPassword();
                    final ServerPost serverPost = new ServerPost(Urls.ADDSTALKER.url, v.getContext());
                    serverPost.addToPost("name", login.getText().toString());

                    //date and id are figured out by server
                    serverPost.addToPost("id", "1");
                    serverPost.addToPost("email", email.getText().toString());
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    serverPost.addToPost("dateOfJoining", timeStamp);
                    serverPost.addToPost("lastOnline", timeStamp);
                    serverPost.addToPost("password", passwd);
//                    Log.d("reg data",serverPost.getParams().toString());
                    boolean emailMatch=checkEmail(email.getText().toString());
                    if (!emailMatch) {
                        showDialog("Enter correct email address", false);

                    } else if(emailMatch) {
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                serverPost.postStalker();
                            }
                        });
                        th.run();

                        try {
                            th.join();
                            debug.setVisibility(View.INVISIBLE);
                            Log.d("passwordcheck", serverPost.getReturnedValue());
                            if (!serverPost.getReturnedValue().equals(" ")) {
                                Log.d("if", "working");
                                showDialog("Successfully signed up", true);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else{
                    showDialog("Passwords do not match", false);
                }
            }
        });
    }

    protected void hashPassword() {
        passwd = password.getText().toString();
        passwd = Hashing.sha256().hashString(passwd, StandardCharsets.UTF_8).toString();
    }

    boolean checkpasswords() {
        return password.getText().toString().equals(repassword.getText().toString());

    }

    protected void clear(){
        login.setText("");
        password.setText("");
        repassword.setText("");
        email.setText("");
    }

    private boolean checkEmptyFields(){
        if(login.getText().toString().equals("") ||
           password.getText().toString().equals("") ||
           repassword.getText().toString().equals("") ||
           email.getText().toString().equals(""))
            return true;
        else return false;
    }

    private void showDialog(String message, final boolean closeActvity){
        AlertDialog alertDialog = new AlertDialog.Builder(SignUpFragment.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(closeActvity) SignUpFragment.this.onBackPressed();
                    }
                }).show();
    }

    public boolean checkEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}




