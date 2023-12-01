package com.example.project_task.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.common.model.Pessoa;
import com.example.common.model.Projeto;
import com.example.common.model.Tarefa;
import com.example.common.utils.RequestObject;
import com.example.common.utils.ResponseObject;
import com.example.project_task.network.SocketClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerViewModel extends ViewModel {

    private final SocketClient socketClient;
    private final MutableLiveData<ResponseObject> responseObjectLiveData;
    private String lastRequestOperation = "";
    private int lastTeamId = -1; // Armazenar o último teamId usado
    private final MutableLiveData<Pessoa> loggedUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<Integer, List<Projeto>>> projectsByTeamLiveData = new MutableLiveData<>();
    private final MutableLiveData<Projeto> projectDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Tarefa>> projectTasksLiveData = new MutableLiveData<>();

    public ServerViewModel() {
        responseObjectLiveData = new MutableLiveData<>();
        socketClient = SocketClient.getInstance(responseObjectLiveData);
        observeResponses();
    }

    private void observeResponses() {
        responseObjectLiveData.observeForever(responseObject -> {
            if (responseObject != null) {
                Log.d(TAG, "Resposta recebida: " + responseObject);
                processResponse(responseObject);
            }
        });
    }

    private void processResponse(ResponseObject responseObject) {
        switch (lastRequestOperation) {
            case "login":
                if (responseObject.isSuccess()) {
                    Pessoa user = (Pessoa) responseObject.getData();
                    if (user != null && user.getEquipes() != null && !user.getEquipes().isEmpty()) {
                        setLoggedUser(user); //salva o user no liveData
                    } else {
                        loginErrorMessage.setValue("O usuário não pertence a nenhuma equipe.");
                    }
                } else {
                    loginErrorMessage.setValue(responseObject.getMessage());
                }
                break;
            case "listarProjetosPorEquipe":
                processProjectsResponse(responseObject, lastTeamId);
                break;
            case "lerProjeto":
                processProjectDetailsResponse(responseObject);
                break;
            case "listarTarefasPorProjeto":
                processTasksResponse(responseObject);
                break;
            // ... Add casos para outras operações ...
        }
        lastRequestOperation = "";
        lastTeamId = -1; // Reseta o lastTeamId após processar a resposta
    }

    public MutableLiveData<Pessoa> getLoggedUserLiveData() { return loggedUserLiveData; }

    public MutableLiveData<String> getLoginErrorMessage() { return loginErrorMessage; }

    public void sendRequest(RequestObject request) {
        lastRequestOperation = request.getOperation();
        Log.d(TAG, "Enviando requisição: " + request);
        socketClient.sendRequest(request);
    }

    public void setLoggedUser(Pessoa user) {
        Log.d(TAG, "Usuário logado definido: " + user);
        loggedUserLiveData.setValue(user);
    }

    public void logout() {
        // Limpa o estado do usuário logado
        loggedUserLiveData.setValue(null);
    }

    public void resetResponseObjectLiveData() {
        responseObjectLiveData.setValue(null);
    }

    public LiveData<Map<Integer, List<Projeto>>> getProjectsByTeamLiveData() {
        return projectsByTeamLiveData;
    }

    public LiveData<Projeto> getProjectDetailsLiveData() {
        return projectDetailsLiveData;
    }

    public LiveData<List<Tarefa>> getProjectTasksLiveData() {
        return projectTasksLiveData;
    }

    public void fetchProjectsForTeam(int teamId) {
        lastTeamId = teamId; // Armazena o teamId para uso posterior
        lastRequestOperation = "listarProjetosPorEquipe";
        RequestObject request = new RequestObject("listarProjetosPorEquipe", teamId);
        socketClient.sendRequest(request);
    }

    public void fetchTasksForProject(int projectId) {
        lastRequestOperation = "listarTarefasPorProjeto";
        RequestObject request = new RequestObject("listarTarefasPorProjeto", projectId);
        socketClient.sendRequest(request);
    }

    private void processProjectsResponse(ResponseObject responseObject, int teamId) {
        if (responseObject.isSuccess()) {
            List<Projeto> projects = (List<Projeto>) responseObject.getData();
            Log.d(TAG, "Projetos recebidos para a equipe " + teamId + ": " + projects);
            Map<Integer, List<Projeto>> currentProjects = projectsByTeamLiveData.getValue();
            if (currentProjects == null) {
                currentProjects = new HashMap<>();
            }
            currentProjects.put(teamId, projects);
            projectsByTeamLiveData.setValue(currentProjects);
        } else {
            Log.e(TAG, "Erro ao buscar projetos: " + responseObject.getMessage());
        }
    }


    private void processProjectDetailsResponse(ResponseObject responseObject) {
        Log.e(TAG, "Processando resultado dos detalhes do projeto: " + responseObject.getMessage());
        if (responseObject.isSuccess()) {
            Projeto projectDetails = (Projeto) responseObject.getData();
            projectDetailsLiveData.setValue(projectDetails);
        } else {
            Log.e(TAG, "Erro ao receber detalhes do projeto: " + responseObject.getMessage());
        }
    }

    private void processTasksResponse(ResponseObject responseObject) {
        if (responseObject.isSuccess()) {
            List<Tarefa> tarefas = (List<Tarefa>) responseObject.getData();
            projectTasksLiveData.setValue(tarefas);
        } else {
            Log.e(TAG, "Erro ao receber tarefas do projeto: " + responseObject.getMessage());
        }
    }

    public void clearTasks() {
        projectTasksLiveData.setValue(new ArrayList<>()); // Limpa a lista de tarefas
    }

    public void updateTask(Tarefa tarefa) {
        lastRequestOperation = "atualizarTarefa";
        RequestObject request = new RequestObject("atualizarTarefa", tarefa);
        socketClient.sendRequest(request);
    }

    @Override
    protected void onCleared() {
        socketClient.closeConnection();
        super.onCleared();
    }
}
