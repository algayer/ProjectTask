package com.example.project_task.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.common.model.Pessoa;
import com.example.common.model.Projeto;
import com.example.common.utils.RequestObject;
import com.example.common.utils.ResponseObject;
import com.example.project_task.network.SocketClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerViewModel extends ViewModel {

    private final SocketClient socketClient;
    private final MutableLiveData<ResponseObject> responseObjectLiveData;
    private final MutableLiveData<Pessoa> loggedUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<Integer, List<Projeto>>> projectsByTeamLiveData = new MutableLiveData<>();

    public ServerViewModel() {
        responseObjectLiveData = new MutableLiveData<>();
        socketClient = SocketClient.getInstance(responseObjectLiveData);
    }

    public LiveData<ResponseObject> getResponseObjectLiveData() {
        return responseObjectLiveData;
    }

    public MutableLiveData<Pessoa> getLoggedUserLiveData() {
        return loggedUserLiveData;
    }

    public void sendRequest(RequestObject request) {
        socketClient.sendRequest(request);
    }

    public void setLoggedUser(Pessoa user) {
        loggedUserLiveData.setValue(user);
    }

    public void resetResponseObjectLiveData() {
        responseObjectLiveData.setValue(null);
    }

    public LiveData<Map<Integer, List<Projeto>>> getProjectsByTeamLiveData() {
        return projectsByTeamLiveData;
    }

    public void fetchProjectsForTeam(int idEquipe) {
        RequestObject request = new RequestObject("listarProjetosPorEquipe", idEquipe);
        socketClient.sendRequest(request);
    }

    public void handleServerResponse() {
        responseObjectLiveData.observeForever(responseObject -> {
            if (responseObject != null) {
                switch (responseObject.getMessage()) {
                    case "login":
                        if (responseObject.isSuccess()) {
                            Pessoa user = (Pessoa) responseObject.getData();
                            loggedUserLiveData.setValue(user);
                        }
                        break;
                    case "listarProjetosPorEquipe":
                        if (responseObject.isSuccess()) {
                            int idEquipe = (int) responseObject.getData();
                            List<Projeto> projects = (List<Projeto>) responseObject.getData();
                            Map<Integer, List<Projeto>> currentProjects = projectsByTeamLiveData.getValue();
                            if (currentProjects == null) {
                                currentProjects = new HashMap<>();
                            }
                            currentProjects.put(idEquipe, projects);
                            projectsByTeamLiveData.postValue(currentProjects);
                        }
                        break;
                    // Add other cases as needed
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        socketClient.closeConnection();
        super.onCleared();
    }
}
