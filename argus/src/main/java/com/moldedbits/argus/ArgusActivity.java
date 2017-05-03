package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.listener.SignUpListener;
import com.moldedbits.argus.model.ArgusUser;

public class ArgusActivity extends AppCompatActivity implements LoginListener, SignUpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }
        if (ArgusSessionManager.isLoggedIn()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, LoginFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, SignupFragment.newInstance())
                    .commit();
        }
    }

    public void onLoginSuccess(ArgusUser user) {
        Argus.getInstance().loginUser(user);
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onLoginFailure(String message) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignupSuccess(ArgusUser user) {
        Argus.getInstance().loginUser(user);
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onSignupFailure() {
        //TODO handle failure correctly
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
}
