package com.example.stocktakingtech.Model;

public class DistribuidorModel {

    private String NOMBRE_DISTRIBUIDOR;
    private String IDENTIFICACION_DISTRIBUIDOR;
    private String TIPO_PERSONA;
    private String DIRECCION_DISTRIBUIDOR;
    private String ACTIVO_DISTRIBUIDOR;
    private String COD_DISTRIBUIDOR;

    public DistribuidorModel(String COD_DISTRIBUIDOR, String NOMBRE_DISTRIBUIDOR) {
        this.NOMBRE_DISTRIBUIDOR = NOMBRE_DISTRIBUIDOR;
        this.COD_DISTRIBUIDOR = COD_DISTRIBUIDOR;
    }

    public DistribuidorModel() {

    }

    public String getCOD_DISTRIBUIDOR() {
        return COD_DISTRIBUIDOR;
    }

    public void setCOD_DISTRIBUIDOR(String COD_DISTRIBUIDOR) {
        this.COD_DISTRIBUIDOR = COD_DISTRIBUIDOR;
    }

    public String getNOMBRE_DISTRIBUIDOR() {
        return NOMBRE_DISTRIBUIDOR;
    }

    public void setNOMBRE_DISTRIBUIDOR(String NOMBRE_DISTRIBUIDOR) {
        this.NOMBRE_DISTRIBUIDOR = NOMBRE_DISTRIBUIDOR;
    }

    public String getIDENTIFICACION_DISTRIBUIDOR() {
        return IDENTIFICACION_DISTRIBUIDOR;
    }

    public void setIDENTIFICACION_DISTRIBUIDOR(String IDENTIFICACION_DISTRIBUIDOR) {
        this.IDENTIFICACION_DISTRIBUIDOR = IDENTIFICACION_DISTRIBUIDOR;
    }

    public String getTIPO_PERSONA() {
        return TIPO_PERSONA;
    }

    public void setTIPO_PERSONA(String TIPO_PERSONA) {
        this.TIPO_PERSONA = TIPO_PERSONA;
    }

    public String getDIRECCION_DISTRIBUIDOR() {
        return DIRECCION_DISTRIBUIDOR;
    }

    public void setDIRECCION_DISTRIBUIDOR(String DIRECCION_DISTRIBUIDOR) {
        this.DIRECCION_DISTRIBUIDOR = DIRECCION_DISTRIBUIDOR;
    }

    public String getACTIVO_DISTRIBUIDOR() {
        return ACTIVO_DISTRIBUIDOR;
    }

    public void setACTIVO_DISTRIBUIDOR(String ACTIVO_DISTRIBUIDOR) {
        this.ACTIVO_DISTRIBUIDOR = ACTIVO_DISTRIBUIDOR;
    }

    @Override
    public String toString(){return NOMBRE_DISTRIBUIDOR;}



}
