package com.example.project_task.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.common.model.Tarefa;
import com.example.project_task.databinding.FragmentTarefaBinding;
import com.example.project_task.viewmodel.ServerViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TarefaFragment extends Fragment {

    private FragmentTarefaBinding binding;
    private Tarefa tarefa;
    private ServerViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTarefaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        if (getArguments() != null) {
            tarefa = (Tarefa) getArguments().getSerializable("tarefa");
            setupTarefaDetails();
        }
    }

    private void setupTarefaDetails() {
        binding.tvNomeTarefa.setText(tarefa.getNome());
        binding.tvDescTarefa.setText(tarefa.getDescricao());
        binding.tvStatusTarefa.setText(tarefa.getEstado() ? "Concluída" : "Pendente");
        binding.tvDataDeEntrega.setText(tarefa.getDataEntrega() != null ? formatDate(tarefa.getDataEntrega()) : "Data de entrega não definida");

        if (tarefa.getHorasTrabalhadas() != null) {
            binding.etHorasTrabalhadas.setText(tarefa.getHorasTrabalhadas());
            binding.btnConcluirTarefa.setText("Editar Horas");
        } else {
            binding.btnConcluirTarefa.setText("Concluir Tarefa");
        }

        binding.btnConcluirTarefa.setOnClickListener(v -> {
            if (validateInput()) {
                tarefa.setHorasTrabalhadas(binding.etHorasTrabalhadas.getText().toString().trim());
                tarefa.setEstado(true); // Marca como concluída
                viewModel.updateTask(tarefa); // Tarefa enviada para servidor para ser atualizada
                Toast.makeText(getContext(), "Tarefa atualizada!", Toast.LENGTH_SHORT).show();

                // Volta ao fragment anterior
                if (getView() != null) {
                    NavController navController = Navigation.findNavController(getView());
                    navController.popBackStack();
                }
            } else {
                Toast.makeText(getContext(), "Por favor, preencha as horas trabalhadas (hh:mm:ss).", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput() {
        String horasInput = binding.etHorasTrabalhadas.getText().toString().trim();
        // Regex para validar o tempo no formato HH:mm:ss
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

        return horasInput.matches(regex);
    }


    private String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
