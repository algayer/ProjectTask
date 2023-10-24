package com.example.common.utils;

import java.io.Serializable;

public class RequestObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation; // Operação que você deseja realizar (por exemplo, "create", "read", "update", "delete")
    private Object data; // Os dados relacionados à operação

    public RequestObject() {
        // Construtor vazio é necessário para serialização
    }

    public RequestObject(String operation, Object data) {
        this.operation = operation;
        this.data = data;
    }

    // Getter e Setter para operation
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        return "RequestObject{" +
                "operation='" + operation + '\'' +
                ", data=" + data +
                '}';
    }
}
