package com.example.project_task.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.common.model.Pessoa;
import com.example.common.utils.RequestObject;
import com.example.project_task.R;
import com.example.project_task.databinding.FragmentLoginBinding;
import com.example.project_task.databinding.DialogChangePasswordBinding;
import com.example.project_task.viewmodel.ServerViewModel;

import java.security.MessageDigest;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;
    private ServerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        setupObservers();
        setupClickListeners();
    }

    private void setupObservers() {
        viewModel.getLoggedUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_projectListFragment);
                viewModel.resetResponseObjectLiveData();
            }
        });

        viewModel.getLoginErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                binding.inputUser.setError(errorMessage);
                binding.inputPassword.setError(errorMessage);
                viewModel.getLoginErrorMessage().setValue(null);
            }
        });
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> attemptLogin());
        binding.btnCancelar.setOnClickListener(v -> limpaCampos());
        binding.clickHereText.setOnClickListener(v -> openChangePasswordDialog());
    }

    private void attemptLogin() {
        if (validateFields()) {
            String usuario = binding.inputUser.getText().toString();
            String senhaHash = hashSenha(binding.inputPassword.getText().toString());

            if (senhaHash != null) {
                viewModel.sendRequest(new RequestObject("login", new Pessoa(usuario, senhaHash)));
            } else {
                Toast.makeText(getContext(), "Erro ao processar a senha.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogChangePasswordBinding dialogBinding = DialogChangePasswordBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot())
                .setPositiveButton("Trocar", null) // Define null aqui para sobrescrever depois
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        // Sobrescrevendo o comportamento do botão para validar antes de fechar o diálogo
        dialog.setOnShowListener(dlg -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            // Validar e trocar a senha
            String email = dialogBinding.inputEmail.getText().toString();
            String oldPassword = dialogBinding.inputOldPassword.getText().toString();
            String newPassword = dialogBinding.inputNewPassword.getText().toString();
            String confirmNewPassword = dialogBinding.inputConfirmNewPassword.getText().toString();

            if (validateChangePasswordFields(email, oldPassword, newPassword, confirmNewPassword)) {
                String oldPasswordHash = hashSenha(oldPassword);
                String newPasswordHash = hashSenha(newPassword);
                viewModel.changeUserPassword(email, oldPasswordHash, newPasswordHash);
                dialog.dismiss();
            }
        }));

        dialog.show();
    }

    private boolean validateChangePasswordFields(String email, String oldPassword, String newPassword, String confirmNewPassword) {
        boolean valid = true;

        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, informe o e-mail.", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos de senha.", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(getContext(), "As novas senhas não coincidem.", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private boolean validateFields() {
        boolean valid = true;
        if (binding.inputUser.getText().toString().isEmpty()) {
            binding.inputUser.setError("Erro: informe o usuário.");
            valid = false;
        }

        if (binding.inputPassword.getText().toString().isEmpty()) {
            binding.inputPassword.setError("Erro: informe a senha.");
            valid = false;
        }

        return valid;
    }

    private void limpaCampos() {
        binding.inputUser.setText("");
        binding.inputPassword.setText("");
    }

    private static String hashSenha(String senhaInserida) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senhaInserida.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar hash da senha", e);
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
