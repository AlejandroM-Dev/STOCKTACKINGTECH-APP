package com.example.stocktakingtech.Model;

public class SalidaModel {
    private String COD_SALIDA;
    private String FECHA_COMPRA;
    private String TOTAL_COMPRA;
    private String NOMBRE_COMPRADOR;

    private String COD_USUARIO;

    public SalidaModel(String COD_SALIDA) {
        this.COD_SALIDA = COD_SALIDA;
    }

    public SalidaModel() {
    }

    public String getCOD_SALIDA() {
        return COD_SALIDA;
    }

    public void setCOD_SALIDA(String COD_SALIDA) {
        this.COD_SALIDA = COD_SALIDA;
    }

    public String getFECHA_COMPRA() {
        return FECHA_COMPRA;
    }

    public void setFECHA_COMPRA(String FECHA_COMPRA) {
        this.FECHA_COMPRA = FECHA_COMPRA;
    }

    public String getTOTAL_COMPRA() {
        return TOTAL_COMPRA;
    }

    public void setTOTAL_COMPRA(String TOTAL_COMPRA) {
        this.TOTAL_COMPRA = TOTAL_COMPRA;
    }

    public String getNOMBRE_COMPRADOR() {
        return NOMBRE_COMPRADOR;
    }

    public void setNOMBRE_COMPRADOR(String NOMBRE_COMPRADOR) {
        this.NOMBRE_COMPRADOR = NOMBRE_COMPRADOR;
    }

    public String getCOD_USUARIO() {
        return COD_USUARIO;
    }

    public void setCOD_USUARIO(String COD_USUARIO) {
        this.COD_USUARIO = COD_USUARIO;
    }

    @Override
    public String toString(){return COD_SALIDA;}
}
