package com.example.stocktakingtech.Model;

public class ProductosModel {

    private String COD_PRODUCTO;
    private String NOMBRE_PRODUCTO;
    private String VALOR_UNIDAD;
    private String CANTIDAD_STOCK;
    private String ACTIVO;
    private String COD_DISTRIBUIDOR;
    private String NOMBRE_DISTRIBUIDOR;

    public ProductosModel(String COD_PRODUCTO, String NOMBRE_PRODUCTO, String VALOR_UNIDAD, String CANTIDAD_STOCK) {
        this.COD_PRODUCTO = COD_PRODUCTO;
        this.NOMBRE_PRODUCTO = NOMBRE_PRODUCTO;
        this.VALOR_UNIDAD = VALOR_UNIDAD;
        this.CANTIDAD_STOCK = CANTIDAD_STOCK;
    }

    public ProductosModel() {

    }

    public String getCOD_PRODUCTO() {
        return COD_PRODUCTO;
    }

    public void setCOD_PRODUCTO(String COD_PRODUCTO) {
        this.COD_PRODUCTO = COD_PRODUCTO;
    }

    public String getNOMBRE_PRODUCTO() {
        return NOMBRE_PRODUCTO;
    }

    public void setNOMBRE_PRODUCTO(String NOMBRE_PRODUCTO) {
        this.NOMBRE_PRODUCTO = NOMBRE_PRODUCTO;
    }

    public String getVALOR_UNIDAD() {
        return VALOR_UNIDAD;
    }

    public void setVALOR_UNIDAD(String VALOR_UNIDAD) {
        this.VALOR_UNIDAD = VALOR_UNIDAD;
    }

    public String getCANTIDAD_STOCK() {
        return CANTIDAD_STOCK;
    }

    public void setCANTIDAD_STOCK(String CANTIDAD_STOCK) {
        this.CANTIDAD_STOCK = CANTIDAD_STOCK;
    }

    public String isACTIVO() {
        return ACTIVO;
    }

    public void setACTIVO(String ACTIVO) {
        this.ACTIVO = ACTIVO;
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

    @Override
    public String toString(){return NOMBRE_PRODUCTO;}
}
