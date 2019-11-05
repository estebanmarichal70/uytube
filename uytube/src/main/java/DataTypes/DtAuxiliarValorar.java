/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import clases.AuxiliarValorar;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author esteban
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtAuxiliarValorar implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String dueño;
    
    private String vid;
     
    private String user;
    
    private String val;

    public DtAuxiliarValorar() {
    }

    public DtAuxiliarValorar(String dueño, String vid, String user, String val) {
        this.dueño = dueño;
        this.vid = vid;
        this.user = user;
        this.val = val;
    }

    public DtAuxiliarValorar(AuxiliarValorar v) {
        this.dueño = v.getDueño();
        this.vid = v.getVid();
        this.user = v.getUser();
        this.val = v.getVal();
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDueño() {
        return dueño;
    }

    public String getVid() {
        return vid;
    }

    public String getUser() {
        return user;
    }

    public String getVal() {
        return val;
    }
    
    
}
