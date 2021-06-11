package com.pruebaTecnica.backendPrueba.dto;

public class RespuestaDto {
    private int status;
    private Object data;

    public RespuestaDto() {}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
