package com.example.juanb.ubusiness.models.objects;


import android.app.Application;


public class Myglobalvar extends Application{

    private String someVariable,ID,Nombre,Apellido,Pass,Residencia,Edad,NombreCel,OS,PhoneModel,AndroidVersion,Apilv,PhoneNumber;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

    public String getID() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() { return Nombre; }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() { return Apellido; }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getPass() { return Pass; }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getResidencia() { return Residencia; }

    public void setResidencia(String Residencia) {
        this.Residencia = Residencia;
    }

    public String getNombreCel() { return NombreCel; }

    public void setNombreCel(String NombreCel) {
        this.NombreCel = NombreCel;
    }

    public String getEdad() { return Edad; }

    public void setEdad(String Edad) {
        this.Edad = Edad;
    }

    public String getOS() { return OS; }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getPhoneModel() { return PhoneModel; }

    public void setPhoneModel(String PhoneModel) {
        this.PhoneModel = PhoneModel;
    }

    public String getAndroidVersion() { return AndroidVersion; }

    public void setAndroidVersion(String AndroidVersion) {
        this.AndroidVersion = AndroidVersion;
    }

    public String getApilv() { return Apilv; }

    public void setApilv(String Apilv) {
        this.Apilv = Apilv;
    }

    public String getPhoneNumber() { return PhoneNumber; }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }


}
