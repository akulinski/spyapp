package com.example.albert.spyapp.mainviews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.albert.spyapp.R;
import com.example.albert.spyapp.core.eventbus.GlobalEventBusSingleton;
import com.example.albert.spyapp.core.events.StalkerAddedEvent;
import com.example.albert.spyapp.core.events.StalkerAddingErrorEvent;
import com.example.albert.spyapp.fragments.PhotosFragment;
import com.example.albert.spyapp.requests.ApiClient;
import com.example.albert.spyapp.requests.ApiInterface;
import com.example.albert.spyapp.requests.callbacks.AddStalkerCallback;
import com.example.albert.spyapp.services.TestOnlineService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class SignUpScreen extends AppCompatActivity {

    private Button confirm;
    private Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private EditText login;
    private EditText email;
    private EditText password;
    private EditText repassword;
    private String passwd = "";
    private EditText debug;
    private ApiInterface apiService;
    private EventBus eventBus;

    public void subscribeToEventBus() {

        eventBus = GlobalEventBusSingleton.getInstance().getEventBus();
        eventBus.register(new StalkerAddedEventHandler());
        eventBus.register(new StalkerAddingErrorEventHandler());
    }

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
        apiService = ApiClient.getClient().create(ApiInterface.class);
        subscribeToEventBus();

        confirm.setOnClickListener(v -> {
            if (checkEmptyFields()) showDialog("Fill in all the fields", false);
            else if (checkpasswords()) {
                hashPassword();

                boolean emailMatch = checkEmail(email.getText().toString());
                if (!emailMatch) {
                    showDialog("Enter correct email address", false);

                } else {

                    AddStalkerCallback addStalkerCallback = new AddStalkerCallback();
                    Call<String> call = apiService.addStalker((HashMap<String, String>) createRequestBody());
                    call.enqueue(addStalkerCallback);
                }

            } else {
                showDialog("Passwords do not match", false);
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

    protected void clear() {
        login.setText("");
        password.setText("");
        repassword.setText("");
        email.setText("");
    }

    private boolean checkEmptyFields() {
        if (login.getText().toString().equals("") ||
                password.getText().toString().equals("") ||
                repassword.getText().toString().equals("") ||
                email.getText().toString().equals(""))
            return true;
        else return false;
    }

    private void showDialog(String message, final boolean closeActvity) {
        AlertDialog alertDialog = new AlertDialog.Builder(SignUpScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (closeActvity) SignUpScreen.this.onBackPressed();
                    }
                }).show();
    }

    public boolean checkEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private Map<String, String> createRequestBody() {

        HashMap<String, String> body = new HashMap<>();
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        body.put("name", login.getText().toString());
        body.put("id", "1");
        body.put("email", email.getText().toString());
        body.put("dateOfJoining", timeStamp);
        body.put("lastOnline", timeStamp);
        body.put("password", passwd);

        return body;
    }

    private final class StalkerAddedEventHandler {

        @Subscribe
        public void handleStalkerAddedEvent(StalkerAddedEvent event) {
            showDialog(event.getMessage(), false);
            startActivity(new Intent(getApplicationContext(), LoginScreen.class));

        }
    }

    private final class StalkerAddingErrorEventHandler {

        @Subscribe
        public void handleStalkerAddingErrorEvent(StalkerAddingErrorEvent event) {
            showDialog(event.getMessage(), false);
        }
    }
}




