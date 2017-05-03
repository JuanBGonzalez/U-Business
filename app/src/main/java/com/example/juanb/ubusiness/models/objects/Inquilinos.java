package com.example.juanb.ubusiness.models.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juanb on 02/02/17.
 */

public class Inquilinos implements Parcelable {
    private String todoContent;
    private String todoCreationDate;
    private String nombre;
    private String apellido;
    private String cuarto;
    private String cedula;
    private String monto;
    private String diapago;

    /**
     * @param todoCreationDate - Date which the to do was create
     * @param todoContent      - Description of what the to do is supposed to remind the user of
     */
    public Inquilinos(String todoContent, String todoCreationDate,String nombre,String apellido,String cuarto,String cedula,String monto,String diapago) {
        this.todoContent = todoContent;
        this.todoCreationDate = todoCreationDate;

        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.cuarto = cuarto;
        this.monto = monto;
        this.diapago = diapago;
    }

    protected Inquilinos(Parcel in) {
        todoContent = in.readString();
        todoCreationDate = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        cedula = in.readString();
        cuarto = in.readString();
        monto = in.readString();
        diapago = in.readString();
    }

    public static final Parcelable.Creator<Inquilinos> CREATOR = new Parcelable.Creator<Inquilinos>() {
        @Override
        public Inquilinos createFromParcel(Parcel in) {
            return new Inquilinos(in);
        }

        @Override
        public Inquilinos[] newArray(int size) {
            return new Inquilinos[size];
        }
    };

    public String getTodoContent() {
        return todoContent;
    }

    public void setTodoContent(String todoContent) {

        this.todoContent = todoContent;
    }

    public String getTodoCreationDate() {

        return todoCreationDate;
    }

    public void setTodoCreationDate(String todoCreationData) {
        this.todoCreationDate = todoCreationData;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCuarto() {
        return cuarto;
    }

    public void setCuarto(String cuarto) {
        this.cuarto = cuarto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDiapago() {
        return diapago;
    }

    public void setDiapago(String diapago) {
        this.diapago = diapago;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(todoContent);
        parcel.writeString(todoCreationDate);
        parcel.writeString(nombre);
        parcel.writeString(apellido);
        parcel.writeString(cedula);
        parcel.writeString(cuarto);
        parcel.writeString(monto);
        parcel.writeString(diapago);
    }
}
