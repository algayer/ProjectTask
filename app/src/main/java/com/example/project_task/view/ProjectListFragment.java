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

import com.example.common.model.Equipe;
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

        // Inicializa o RecyclerView
        RecyclerView recyclerView = binding.rvProjects;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        projectListAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(projectListAdapter);

        // Observa o usuário logado para obter a lista de equipes do usuário
        viewModel.getLoggedUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null && user.getListEquipe() != null && !user.getListEquipe().isEmpty()) {
                // Limpa a lista de projetos existente
                projectListAdapter.setProjects(new ArrayList<>());
                // Atualiza a UI para cada mudança na lista de projetos por equipe
                viewModel.getProjectsByTeamLiveData().observe(getViewLifecycleOwner(), projectsByTeam -> {
                    List<Projeto> allProjects = new ArrayList<>();
                    for (List<Projeto> teamProjects : projectsByTeam.values()) {
                        allProjects.addAll(teamProjects);
                    }
                    updateProjectList(allProjects);
                });
                // Busca os projetos para cada equipe do usuário
                for (Equipe equipe : user.getListEquipe()) {
                    viewModel.fetchProjectsForTeam(equipe.getID_Equipe());
                }
            }
        });

        projectListAdapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Projeto projeto) {
                // Implemente a lógica de clique aqui, como navegação
            }
        });

        // Configura a caixa de pesquisa
        setupSearchBox();
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
        Log.d(TAG, "Atualizando lista de projetos com " + projects.size() + " projetos.");
        projectListAdapter.setProjects(projects);
        projectListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}