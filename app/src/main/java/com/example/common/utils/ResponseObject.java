package com.example.common.utils;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success; // Indica se a operação foi bem-sucedida
    private String message; // Mensagem informativa sobre o resultado da operação
    private Object data; // Os dados retornados pela operação (por exemplo, um objeto Pessoa)

    public ResponseObject() {
        // Construtor vazio é necessário para serialização
    }

    public ResponseObject(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter e Setter para success
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter e Setter para message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter e Setter para data
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
