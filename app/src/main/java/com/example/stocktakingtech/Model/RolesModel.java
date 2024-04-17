package com.example.stocktakingtech.Model;

public class RolesModel {

    private String ID_ROL;
    private String NOMBRE_ROL;
    private String CREAR;
    private String EDITAR;
    private String ANULAR;
    private String CONSULTAR;
    private String ACTIVO;

    public RolesModel(String ID_ROL, String NOMBRE_ROL) {
        this.NOMBRE_ROL = NOMBRE_ROL;
        this.ID_ROL = ID_ROL;
    }

    public RolesModel() {

    }

    public String getID_ROL() {
        return ID_ROL;
    }

    public void setID_ROL(String ID_ROL) {
        this.ID_ROL = ID_ROL;
    }

    public String getNOMBRE_ROL() {
        return NOMBRE_ROL;
    }

    public void setNOMBRE_ROL(String NOMBRE_ROL) {
        this.NOMBRE_ROL = NOMBRE_ROL;
    }

    public String getCREAR() {
        return CREAR;
    }

    public void setCREAR(String CREAR) {
        this.CREAR = CREAR;
    }

    public String getEDITAR() {
        return EDITAR;
    }

    public void setEDITAR(String EDITAR) {
        this.EDITAR = EDITAR;
    }

    public String getANULAR() {
        return ANULAR;
    }

    public void setANULAR(String ANULAR) {
        this.ANULAR = ANULAR;
    }

    public String getCONSULTAR() {
        return CONSULTAR;
    }

    public void setCONSULTAR(String CONSULTAR) {
        this.CONSULTAR = CONSULTAR;
    }

    public String getACTIVO() {
        return ACTIVO;
    }

    public void setACTIVO(String ACTIVO) {
        this.ACTIVO = ACTIVO;
    }

    public String toString(){return NOMBRE_ROL;}
}
