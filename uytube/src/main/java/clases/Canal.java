/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import DataTypes.DtCanal;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

/**
 *
 * @author esteban
 */
@Entity
@Table(name = "canal_usuario")
public class Canal implements Serializable, Comparable<Canal> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nombre_canal")
    private String nombre_canal;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "isPrivate")
    private Boolean privado;

    @OneToOne(mappedBy = "canal")
    private Usuario usuario;

    public Canal() {
    }

    public Canal(String nombre_canal, String descripcion, Boolean privado, Usuario usuario) {
        this.nombre_canal = nombre_canal;
        this.descripcion = descripcion;
        this.privado = privado;
        this.usuario = usuario;
    }

    public Canal(String nombre_canal, String descripcion, Boolean privado) {
        this.nombre_canal = nombre_canal;
        this.descripcion = descripcion;
        this.privado = privado;
    }

    public Canal(DtCanal canal) {
        this.nombre_canal = canal.getNombre_canal();
        this.descripcion = canal.getDescripcion();
        this.privado = canal.getPrivado();
        if(canal.getUsuario() != null) {
            this.usuario = new Usuario(canal.getUsuario());
        }
    }

    public String getNombre_canal() {
        return nombre_canal;
    }

    public void setNombre_canal(String nombre_canal) {
        this.nombre_canal = nombre_canal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Canal{" + "nombre_canal=" + nombre_canal + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nombre_canal);
        hash = 29 * hash + Objects.hashCode(this.descripcion);
        hash = 29 * hash + Objects.hashCode(this.privado);
        hash = 29 * hash + Objects.hashCode(this.usuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Canal other = (Canal) obj;
        if (!Objects.equals(this.nombre_canal, other.nombre_canal)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.privado, other.privado)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }
    
    @Override
    public int compareTo(Canal other) {
     return this.nombre_canal.compareTo(other.nombre_canal);
   }

}
