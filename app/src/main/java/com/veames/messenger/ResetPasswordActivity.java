package com.veames.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";

    private EditText editTextEmail;
    private Button buttonResetPassword;

    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        initViews();
        observeViewModel();

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        editTextEmail.setText(email);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            R.string.error_empty_fields,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    viewModel.resetPassword(email);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSucceeded) {
                if (isSucceeded) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            R.string.reset_link_sent,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            errorMessage,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}