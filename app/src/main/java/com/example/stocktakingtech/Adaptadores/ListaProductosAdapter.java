package com.example.stocktakingtech.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.R;

import java.util.ArrayList;

public class ListaProductosAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    String nombreP;
    String precioP;
    String cantidadP;

    public ListaProductosAdapter(Context context, String nombre, String precio, String cantidad){
        this.context = context;
        this.nombreP = nombre;
        this.precioP = precio;
        this.cantidadP = cantidad;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final View vista = inflater.inflate(R.layout.lista_item_producto, null);
        TextView nombre = (TextView) vista.findViewById(R.id.viewNombreProducto);
        TextView precio = (TextView) vista.findViewById(R.id.viewPrecioProducto);
        TextView cantidad = (TextView) vista.findViewById(R.id.viewCantidadProducto);

        nombre.setText(nombreP);
        precio.setText(precioP);
        cantidad.setText(cantidadP);

        return vista;
    }
}
