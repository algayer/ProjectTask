package com.example.project_task.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Projeto;
import com.example.common.model.Tarefa;
import com.example.project_task.R;
import com.example.project_task.adapter.TarefaAdapter;
import com.example.project_task.databinding.FragmentProjectDetailBinding;
import com.example.project_task.viewmodel.ServerViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectDetailFragment extends Fragment implements TarefaAdapter.OnTarefaClickListener {
    private static final String TAG = "ProjectDetailFragment";
    private FragmentProjectDetailBinding binding;
    private ServerViewModel viewModel;

    private Projeto projeto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        // Recebendo o projeto do Bundle
        projeto = getArguments() != null ? (Projeto) getArguments().getSerializable("projeto") : null;

        if (projeto != null) {
            configureProjectDetailsInView(projeto); // Configurar os detalhes do projeto
            viewModel.fetchTasksForProject(projeto.getID_Projeto()); // Buscar tarefas para o projeto
        }

        viewModel.getProjectTasksLiveData().observe(getViewLifecycleOwner(), this::updateTaskList);

        // Configuração dos RecyclerViews
        setupRecyclerView(binding.recyclerViewTarefasPendentes, new ArrayList<>(), this);
        setupRecyclerView(binding.recyclerViewTarefasConcluidas, new ArrayList<>(), this);
    }

    private void configureProjectDetailsInView(Projeto projectDetails) {
        binding.tvNomeProjeto.setText(projectDetails.getNome());
        binding.tvDescProjectDetail.setText(projectDetails.getDescricao());
        binding.tvDataInicial.setText(formatDate(projectDetails.getDataInicial()));
        binding.tvDataFinal.setText(formatDate(projectDetails.getDataEntrega()));
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Tarefa> tarefas, TarefaAdapter.OnTarefaClickListener listener) {
        TarefaAdapter adapter = new TarefaAdapter(tarefas, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateTaskList(List<Tarefa> tarefas) {
        List<Tarefa> tarefasPendentes = new ArrayList<>();
        List<Tarefa> tarefasConcluidas = new ArrayList<>();

        for (Tarefa tarefa : tarefas) {
            if (tarefa.getEstado()) {
                tarefasConcluidas.add(tarefa);
            } else {
                tarefasPendentes.add(tarefa);
            }
        }

        // Imprimindo tarefas pendentes
        System.out.println("Tarefas Pendentes:");
        for (Tarefa tarefaPendente : tarefasPendentes) {
            System.out.println(tarefaPendente);
        }

        // Imprimindo tarefas concluídas
        System.out.println("Tarefas Concluídas:");
        for (Tarefa tarefaConcluida : tarefasConcluidas) {
            System.out.println(tarefaConcluida);
        }

        // Configura os RecyclerViews com as listas atualizadas
        setupRecyclerView(binding.recyclerViewTarefasPendentes, tarefasPendentes, this);
        setupRecyclerView(binding.recyclerViewTarefasConcluidas, tarefasConcluidas, this);
    }

    private String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.clearTasks();
        binding = null;
    }

    @Override
    public void onTarefaClick(Tarefa tarefa) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tarefa", tarefa);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_projectDetailFragment_to_tarefaFragment, bundle);
    }
}
