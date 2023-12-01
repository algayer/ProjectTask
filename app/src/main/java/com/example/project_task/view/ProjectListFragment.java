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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Equipe;
import com.example.common.model.Pessoa;
import com.example.common.model.Projeto;
import com.example.project_task.R;
import com.example.project_task.adapter.ProjectAdapter;
import com.example.project_task.databinding.FragmentProjectListBinding;
import com.example.project_task.viewmodel.ServerViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProjectListFragment extends Fragment {
    private static final String TAG = "ProjectListFragment";
    private FragmentProjectListBinding binding;
    private ServerViewModel viewModel;
    private ProjectAdapter projectListAdapter;
    private Set<Integer> fetchedTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);
        fetchedTeams = new HashSet<>(); // Inicializa o conjunto de IDs de equipe

        setupRecyclerView();

        // Observa o usuário logado para obter a lista de equipes do usuário
        viewModel.getLoggedUserLiveData().observe(getViewLifecycleOwner(), this::handleUserTeams);

        projectListAdapter.setOnItemClickListener(projeto -> navigateToProjectDetails(view, projeto));
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rvProjects;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        projectListAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(projectListAdapter);
        Log.d(TAG, "RecyclerView e Adapter inicializados.");
    }

    private void handleUserTeams(Pessoa user) {
        if (user != null && user.getEquipes() != null && !user.getEquipes().isEmpty()) {
            Log.d(TAG, "Usuário logado com equipes: " + user.getEquipes());
            for (Equipe equipe : user.getEquipes()) {
                if (!fetchedTeams.contains(equipe.getID_Equipe())) {
                    Log.d(TAG, "Buscando projetos para a equipe: " + equipe.getID_Equipe());
                    viewModel.fetchProjectsForTeam(equipe.getID_Equipe());
                    fetchedTeams.add(equipe.getID_Equipe());
                }
            }
        } else {
            Log.d(TAG, "Usuário logado não possui equipes ou dados do usuário são nulos.");
        }

        // Observa mudanças na lista de projetos por equipe
        viewModel.getProjectsByTeamLiveData().observe(getViewLifecycleOwner(), this::updateProjectList);

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

    private void navigateToProjectDetails(View view, Projeto projeto) {
        Bundle bundle = new Bundle();
        //bundle.putInt("project_id", projectId);
        bundle.putSerializable("projeto", projeto);
        Navigation.findNavController(view).navigate(R.id.action_projectListFragment_to_projectDetailFragment, bundle);
    }

    private void updateProjectList(Map<Integer, List<Projeto>> projectsByTeam) {
        List<Projeto> allProjects = new ArrayList<>();
        for (List<Projeto> teamProjects : projectsByTeam.values()) {
            allProjects.addAll(teamProjects);
        }
        projectListAdapter.setProjects(allProjects);
        projectListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getProjectsByTeamLiveData().removeObservers(getViewLifecycleOwner());
        binding = null;
    }
}
