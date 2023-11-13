package com.example.project_task.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.common.utils.ResponseObject;
import com.example.project_task.R;
import com.example.project_task.databinding.FragmentLoginBinding;
import com.example.common.model.Pessoa;
import com.example.common.utils.RequestObject;
import com.example.project_task.viewmodel.ServerViewModel;

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

        viewModel.getResponseObjectLiveData().observe(getViewLifecycleOwner(), responseObject -> {
            if (responseObject != null) {
                Log.d(TAG, "Resposta recebida: " + responseObject);
                handleServerResponse(responseObject);
            } else {
                Log.e(TAG, "Resposta recebida é nula");
            }
        });

        binding.inputUser.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                clearErrorMessages();
            }
        });

        binding.inputPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                clearErrorMessages();
            }
        });


        binding.btnLogin.setOnClickListener(view1 -> {
            Log.d(TAG, "Botão de login clicado");
            if (validateFields()) {
                String usuario = binding.inputUser.getText().toString();
                String senha = binding.inputPassword.getText().toString();

                RequestObject request = new RequestObject("login", new Pessoa(usuario, senha));
                viewModel.sendRequest(request);
                Log.d(TAG, "Requisição de login enviada");
            }
        });

        binding.btnCancelar.setOnClickListener(view12 -> limpaCampos());
    }

    private void clearErrorMessages() {
        binding.inputUser.setError(null);
        binding.inputPassword.setError(null);
    }

    private void handleServerResponse(ResponseObject responseObject) {
        if (responseObject.isSuccess()) {
            Log.d(TAG, "Login bem-sucedido");
            Object data = responseObject.getData();
            if (data instanceof Pessoa) {
                Pessoa user = (Pessoa) data;
                if (user.getListEquipe() != null && !user.getListEquipe().isEmpty()) {
                    // Usuário possui equipes, pode proceder
                    viewModel.setLoggedUser(user);
                    Log.d(TAG, "Usuário logado: " + user);
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_projectListFragment);
                    viewModel.resetResponseObjectLiveData();
                } else {
                    // Usuário não possui equipes, mostre uma mensagem ou trate o caso
                    Log.e(TAG, "O usuário não pertence a nenhuma equipe.");
                    // Mostre alguma mensagem de erro ou instrução ao usuário
                }
                limpaCampos();
            }
        } else {
            Log.e(TAG, "Erro no login: " + responseObject.getMessage());
            binding.inputUser.setError(responseObject.getMessage());
            binding.inputPassword.setError(responseObject.getMessage());
        }
    }


    private boolean validateFields() {
        if (binding.inputUser.getText().toString().isEmpty()) {
            binding.inputUser.setError("Erro: informe o usuário.");
            binding.inputUser.requestFocus();
            return false;
        }

        if (binding.inputPassword.getText().toString().isEmpty()) {
            binding.inputPassword.setError("Erro: informe a senha.");
            binding.inputPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void limpaCampos() {
        binding.inputUser.setText("");
        binding.inputPassword.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Classe auxiliar para lidar com mudanças de texto
    private abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
