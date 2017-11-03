package leema.com.daytrip1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public static final String HEADER_PREFS = "Header prefs";
    public static final String DISPLAY_NAME_KEY = "username";

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
    private EditText mPasswordView;
    private EditText mConfirmPass;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mNameView = (AutoCompleteTextView) findViewById(R.id.register_username);
        mPasswordView = findViewById(R.id.register_password);
        mConfirmPass = findViewById(R.id.register_confirmpass);

        mAuth = FirebaseAuth.getInstance();

    }

    public void signUp(View v) {
        attemptRegister();
    }

    private void attemptRegister() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNameView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPass = mConfirmPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Password is too short or does not match");
            focusView = mPasswordView;
            cancel = true;
        }
        if(TextUtils.isEmpty(email)) {
            mEmailView.setError("Please enter email");
            focusView= mEmailView;
            cancel = true;
        } else if(!isEmailValid(email)) {
            mEmailView.setError("Please enter valid email");
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            createFireBaseUser();
        }
    }

    private boolean isPasswordValid(String password) {
        String confirmPass = mConfirmPass.getText().toString();
        return confirmPass.matches(password) && password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void createFireBaseUser() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    showErrorDialog("Registration attempt failed, please try again later");
                } else {
                    saveDisplayName();
                    Intent goToLogin = new Intent(RegisterActivity.this, SigninPage.class);
                    startActivity(goToLogin);

                }
            }
        });

    }
    private void saveDisplayName() {
        String displayName = mNameView.getText().toString();
        SharedPreferences namePref = getSharedPreferences(HEADER_PREFS, 0);
        namePref.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
