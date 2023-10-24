package com.example.project_task.network;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

    private final static String TERMINATION_STRING = "TERMINATION_SIGNAL";
    private SocketClient(MutableLiveData<ResponseObject> responseObjectLiveData) {
        this.responseObjectLiveData = responseObjectLiveData;
        this.isConnected = connect();
    }

    public static synchronized SocketClient getInstance(MutableLiveData<ResponseObject> responseObjectLiveData) {
        if (instance == null) {
            instance = new SocketClient(responseObjectLiveData);
        }
        return instance;
    }

    private boolean connect() {
        // Cria uma nova thread para a operação de rede
        new Thread(() -> {
            try {
                Log.d(TAG, "Tentando conectar...");
                socket = new Socket("10.0.2.2", 8080);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                startListener();
                Log.d(TAG, "Conectado com sucesso.");
                isConnected = true;
            } catch (IOException e) {
                Log.e(TAG, "Erro ao conectar", e);
                isConnected = false;
            }
        }).start();

        return isConnected;
    }

    public boolean sendRequest(RequestObject request) {
        new Thread(() -> {
            if (!isConnected) {
                Log.d(TAG, "Não está conectado. Tentando reconectar...");
                isConnected = connect();
                if (!isConnected) {
                    Log.e(TAG, "Falha ao reconectar.");
                    return;
                }
            }
            try {
                Log.d(TAG, "Enviando requisição...");
                out.writeObject(request);
                out.flush();
                Log.d(TAG, "Requisição enviada.");
            } catch (Exception e) {
                Log.e(TAG, "Erro ao enviar requisição: " + e.getMessage());
                e.printStackTrace();
                isConnected = false;
            }
        }).start();
        return true;
    }

    private void startListener() {
        new Thread(() -> {
            try {
                while (isConnected) {
                    Log.d(TAG, "Aguardando resposta...");
                    Object obj = in.readObject();

                    ResponseObject response = (ResponseObject) obj;
                    responseObjectLiveData.postValue(response);
                    Log.d(TAG, "Resposta recebida.");
                }
            } catch (EOFException e) {
                Log.e(TAG, "Conexão perdida com o servidor");
                isConnected = false;
            } catch (Exception e) {
                Log.e(TAG, "Erro ao receber resposta: " + e.getMessage());
                e.printStackTrace();
                isConnected = false;
            }
        }).start();
    }


    public void closeConnection() {
        try {
            Log.d(TAG, "Fechando conexão...");
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            isConnected = false;
            Log.d(TAG, "Conexão fechada.");
        } catch (IOException e) {
            Log.e(TAG, "Erro ao fechar conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
