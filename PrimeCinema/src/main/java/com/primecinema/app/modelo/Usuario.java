package com.primecinema.app.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
        private int id;
        private String login;
        private String password;
        private String nombreCompleto;
        private String dui;
        private String direccion;
        private String correoElectronico;
        private String telefono;

        public Usuario() {
            // Constructor vacío
        }

        // Getters y setters para todos los campos

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public String getDui() {
            return dui;
        }

        public void setDui(String dui) {
            this.dui = dui;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public void setCorreoElectronico(String correoElectronico) {
            this.correoElectronico = correoElectronico;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }


    }
