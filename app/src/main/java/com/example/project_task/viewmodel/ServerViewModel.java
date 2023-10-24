package com.example.project_task.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.common.model.Pessoa;
import com.example.common.model.Projeto;
import com.example.common.utils.RequestObject;
import com.example.common.utils.ResponseObject;
import com.example.project_task.network.SocketClient;

import java.util.List;

public class ServerViewModel extends ViewModel {

    private final SocketClient socketClient;
    private final MutableLiveData<ResponseObject> responseObjectLiveData = new MutableLiveData<>();
    private final MutableLiveData<Pessoa> loggedUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Projeto>> projectsListLiveData = new MutableLiveData<>();

    public ServerViewModel() {
        socketClient = SocketClient.getInstance(responseObjectLiveData);
    }

    public LiveData<ResponseObject> getResponseObjectLiveData() {
        return responseObjectLiveData;
    }

    public void sendRequest(RequestObject request) {
        socketClient.sendRequest(request);
    }

    public void resetResponseObjectLiveData() {
        responseObjectLiveData.setValue(null);
    }

    public MutableLiveData<Pessoa> getLoggedUserLiveData() {
        return loggedUserLiveData;
    }

    public void setLoggedUser(Pessoa user) {
        loggedUserLiveData.setValue(user);
    }

    public MutableLiveData<List<Projeto>> getProjectsListLiveData() {
        return projectsListLiveData;
    }

    public void fetchProjectsForUser(int idPessoa) {
        // Crie a solicitação para buscar projetos para o usuário
        RequestObject request = new RequestObject("listarProjetosPorUsuario", idPessoa);
        // Envie a solicitação usando socketClient
        socketClient.sendRequest(request);
    }



    // Método para lidar com a resposta de buscar projetos para um usuário
    public void handleFetchProjectsResponse(ResponseObject response) {
        if (response != null && "listarProjetosPorUsuario".equals(response.getMessage())) {
            if (response.isSuccess()) {
                List<Projeto> projects = (List<Projeto>) response.getData();
                projectsListLiveData.postValue(projects);
            } else {
                // Trate a falha na resposta, se necessário
                // Por exemplo, exiba uma mensagem de erro ao usuário
            }
        }
    }

    @Override
    protected void onCleared() {
        socketClient.closeConnection();
        super.onCleared();
    }
}
