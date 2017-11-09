package leema.com.daytrip1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninPage extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private ImageButton signInGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null) {
            Intent goToMain = new Intent(SigninPage.this, FullscreenActivity.class);
            startActivity(goToMain);
        }




    }

    public void signInExistingUser(View v) {
        attemptLogin();
    }

    public void registerNewUser(View v) {
        Intent registerPage = new Intent (SigninPage.this, RegisterActivity.class);
        finish();
        startActivity(registerPage);
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(email.equals("") || password.equals("")) {
            return;
        }
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    showErrorDialog("There was an error logging you in");
                } else {
                    Intent goToMain = new Intent(SigninPage.this, FullscreenActivity.class);
                    finish();
                    startActivity(goToMain);
                }
            }
        });
    }
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
