package com.example.stocktakingtech.Model;

public class Usuario {
    String COD_USUARIO, NOMBRE_USUARIO, APELLIDOS_USUARIO, EMAIL, DIRECCION, TELEFONO, CONTRASEÑA, ACTIVO , ID_ROL, IDENTIFICACION_USUARIO;


    public String getCOD_USUARIO() {
        return COD_USUARIO;
    }

    public void setCOD_USUARIO(String COD_USUARIO) {
        this.COD_USUARIO = COD_USUARIO;
    }

    public String getNOMBRE_USUARIO() {
        return NOMBRE_USUARIO;
    }

    public void setNOMBRE_USUARIO(String NOMBRE_USUARIO) {
        this.NOMBRE_USUARIO = NOMBRE_USUARIO;
    }

    public String getAPELLIDOS_USUARIO() {
        return APELLIDOS_USUARIO;
    }

    public void setAPELLIDOS_USUARIO(String APELLIDOS_USUARIO) {
        this.APELLIDOS_USUARIO = APELLIDOS_USUARIO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getCONTRASEÑA() {
        return CONTRASEÑA;
    }

    public void setCONTRASEÑA(String CONTRASEÑA) {
        this.CONTRASEÑA = CONTRASEÑA;
    }

    public String getACTIVO() {
        return ACTIVO;
    }

    public void setACTIVO(String ACTIVO) {
        this.ACTIVO = ACTIVO;
    }

    public String getID_ROL() {
        return ID_ROL;
    }

    public void setID_ROL(String ID_ROL) {
        this.ID_ROL = ID_ROL;
    }

    public String getIDENTIFICACION_USUARIO() {
        return IDENTIFICACION_USUARIO;
    }

    public void setIDENTIFICACION_USUARIO(String IDENTIFICACION_USUARIO) {
        this.IDENTIFICACION_USUARIO = IDENTIFICACION_USUARIO;
    }

    public String toString(){return NOMBRE_USUARIO + " " + APELLIDOS_USUARIO + " " + EMAIL;}
}
