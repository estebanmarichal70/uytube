/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.Normal;
import clases.Usuario;
import excepciones.UsuarioRepetidoException;
import interfaces.IControladorUsuario;
import DataTypes.DtUsuario;
import DataTypes.DtAdministrador;
import DataTypes.DtCanal;
import clases.Administrador;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author tecnologo
 */
public class ControladorUsuario implements IControladorUsuario{
    
    
    @Override
    public void registrarUsuario(DtUsuario u, DtCanal canal, BufferedImage imagen) throws UsuarioRepetidoException{
        ManejadorInformacion mu = ManejadorInformacion.getInstance(); //pido una instancia del manejador
        
        Usuario user = mu.obtenerUsuario(u); //busco si esta o no
        if (user != null)
            throw new UsuarioRepetidoException("El usuario " + user.getNickname() + " ya existe");
        
        if(isAdmin(u)){
            mu.registrarUser(new Administrador(u.getNickname(), u.getContraseña(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen()));
        }else{
            mu.registrarUser(new Normal(u.getNickname(), u.getContraseña(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen()));
        }
        
        if(imagen != null){
            //con esto saco el path de donde esta el proyecto
        String path = System.getProperty("user.dir");
        
        //lo concateno
        path = path + "/src/main/resources/imagenesUsuarios/" + u.getNickname() + ".png";
        
        
        File file = new File(path);
        try {
            ImageIO.write(imagen, "png", file);
        } catch (IOException ex) {
            //Logger.getLogger(SeleccionarImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
     /**
     *
     * @return
     */
    public List listaUsuarios(){
        ManejadorInformacion mu = ManejadorInformacion.getInstance(); //pido una instancia del manejador
        List lista = mu.ObtenerUsuarios();  
        return lista;
    }
    
    /**
     * 
     * @param u recibe un datatype usuario
     * @return devuelve true si el parametro "n" es usuario administrador
     */
    @Override
    public boolean isAdmin(DtUsuario u){
        if(u instanceof DtAdministrador) //pregunto si es admin o no
            return true;
        return false;
    }
    
}
