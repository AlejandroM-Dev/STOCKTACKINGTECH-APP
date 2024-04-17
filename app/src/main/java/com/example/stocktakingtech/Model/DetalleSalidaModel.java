package com.example.stocktakingtech.Model;

public class DetalleSalidaModel {
    private String COD_DETALLE_SALIDA;
    private String COD_SALIDA;
    private String DESCRIPCION_CONCEPTO;
    private String PRECIO;
    private String COD_PRODUCTO;
    private String CANTIDAD;

    public DetalleSalidaModel(String COD_DETALLE_SALIDA) {
        this.COD_DETALLE_SALIDA = COD_DETALLE_SALIDA;
    }

    public DetalleSalidaModel() {
    }

    public String getCOD_DETALLE_SALIDA() {
        return COD_DETALLE_SALIDA;
    }

    public void setCOD_DETALLE_SALIDA(String COD_DETALLE_SALIDA) {
        this.COD_DETALLE_SALIDA = COD_DETALLE_SALIDA;
    }

    public String getCOD_SALIDA() {
        return COD_SALIDA;
    }

    public void setCOD_SALIDA(String COD_SALIDA) {
        this.COD_SALIDA = COD_SALIDA;
    }

    public String getDESCRIPCION_CONCEPTO() {
        return DESCRIPCION_CONCEPTO;
    }

    public void setDESCRIPCION_CONCEPTO(String DESCRIPCION_CONCEPTO) {
        this.DESCRIPCION_CONCEPTO = DESCRIPCION_CONCEPTO;
    }

    public String getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(String PRECIO) {
        this.PRECIO = PRECIO;
    }

    public String getCOD_PRODUCTO() {
        return COD_PRODUCTO;
    }

    public void setCOD_PRODUCTO(String COD_PRODUCTO) {
        this.COD_PRODUCTO = COD_PRODUCTO;
    }

    public String getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(String CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    @Override
    public String toString(){return COD_DETALLE_SALIDA;}
}
