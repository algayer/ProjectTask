package com.example.project_task.network;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.lifecycle.MutableLiveData;
import com.example.common.utils.RequestObject;
import com.example.common.utils.ResponseObject;

public class SocketClient {
    private static SocketClient instance = null;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final MutableLiveData<ResponseObject> responseObjectLiveData;
    private boolean isConnected = false;
    private static final String TAG = "SocketClient";
    private ExecutorService executorService;

    private SocketClient(MutableLiveData<ResponseObject> responseObjectLiveData) {
        this.responseObjectLiveData = responseObjectLiveData;
        initializeExecutorService();
        this.executorService.execute(this::connect);
    }

    public static synchronized SocketClient getInstance(MutableLiveData<ResponseObject> responseObjectLiveData) {
        if (instance == null) {
            instance = new SocketClient(responseObjectLiveData);
        }
        return instance;
    }

    private void initializeExecutorService() {
        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(2);
        }
    }

    private void connect() {
        try {
            Log.d(TAG, "Tentando conectar...");
            socket = new Socket("10.0.2.2", 8080);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            Log.d(TAG, "Conectado com sucesso.");
            startListener();
        } catch (IOException e) {
            Log.e(TAG, "Erro ao conectar", e);
            isConnected = false;
        }
    }

    public void sendRequest(RequestObject request) {
        initializeExecutorService();
        executorService.execute(() -> {
            if (!isConnected) {
                Log.d(TAG, "Não está conectado. Tentando reconectar...");
                connect();
            }
            if (isConnected) {
                try {
                    Log.d(TAG, "Enviando requisição: " + request.toString());
                    out.writeObject(request);
                    out.flush();
                    Log.d(TAG, "Requisição enviada.");
                } catch (IOException e) {
                    Log.e(TAG, "Erro ao enviar requisição: " + e.getMessage(), e);
                    isConnected = false;
                }
            } else {
                Log.e(TAG, "Falha ao reconectar.");
            }
        });
    }

    private void startListener() {
        initializeExecutorService();
        executorService.execute(() -> {
            try {
                while (isConnected) {
                    Log.d(TAG, "Aguardando resposta...");
                    ResponseObject response = (ResponseObject) in.readObject();
                    Log.d(TAG, "Resposta recebida: " + response.toString());
                    responseObjectLiveData.postValue(response);
                    Log.d(TAG, "Resposta processada.");
                }
            } catch (IOException | ClassNotFoundException e) {
                Log.e(TAG, "Erro ao receber resposta: " + e.getMessage(), e);
                isConnected = false;
            }
        });
    }

    public void closeConnection() {
        try {
            Log.d(TAG, "Fechando conexão...");
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            isConnected = false;
            if (!executorService.isShutdown()) {
                executorService.shutdown();
            }
            Log.d(TAG, "Conexão fechada.");
        } catch (IOException e) {
            Log.e(TAG, "Erro ao fechar conexão: " + e.getMessage(), e);
        }
    }
}
