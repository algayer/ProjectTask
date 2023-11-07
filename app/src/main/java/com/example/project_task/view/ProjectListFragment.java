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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Projeto;
import com.example.project_task.adapter.ProjectAdapter;
import com.example.project_task.databinding.FragmentProjectListBinding;
import com.example.project_task.viewmodel.ServerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectListFragment extends Fragment {
    private static final String TAG = "ProjectListFragment";
    private FragmentProjectListBinding binding;
    private ServerViewModel viewModel;
    private ProjectAdapter projectListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        // Observe o loggedUserLiveData do ServerViewModel
        viewModel.getLoggedUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                int idPessoa = user.getID_Pessoa(); // Obtenha o ID da pessoa a partir do objeto Pessoa
                // Assim que o ID do usuário estiver disponível, você pode fazer a request
                viewModel.fetchProjectsForUser(idPessoa);
            }
        });

        // Observando resposta do servidor
        viewModel.getResponseObjectLiveData().observe(getViewLifecycleOwner(), responseObject -> {
            if (responseObject != null) {
                Log.d(TAG, "Resposta recebida: " + responseObject);
                viewModel.handleFetchProjectsResponse(responseObject);
            } else {
                Log.e(TAG, "Resposta recebida é nula");
            }
        });

        // Inicializa o RecyclerView
        RecyclerView recyclerView = binding.rvProjects;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        projectListAdapter = new ProjectAdapter(requireContext(), new ArrayList<>()); // lista correta aqui
        recyclerView.setAdapter(projectListAdapter);

        projectListAdapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Projeto projeto) {
                // Use o Navigation Component para navegar

            }
        });

        // Configura a caixa de pesquisa
        setupSearchBox();

        viewModel.getProjectsListLiveData().observe(getViewLifecycleOwner(),
                this::updateProjectList);

        // Outros eventos ou lógica específica podem ser tratados aqui
    }

    private void setupSearchBox() {
        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                projectListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void updateProjectList(List<Projeto> projects) {
        Log.d(TAG, "Lista de projetos atualizada: " + projects.size() + " projetos");
        projectListAdapter.setProjects(projects);
        projectListAdapter.notifyDataSetChanged(); // Notifica o adaptador sobre a mudança de dados
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
