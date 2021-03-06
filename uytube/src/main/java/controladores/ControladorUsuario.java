/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.Usuario;
import excepciones.UsuarioRepetidoException;
import interfaces.IControladorUsuario;
import DataTypes.DtUsuario;
import DataTypes.DtCanal;
import DataTypes.DtCanalEliminado;
import DataTypes.DtUsuarioEliminado;
import clases.AuxiliarValorar;
import clases.Canal;
import clases.CanalEliminado;
import clases.ListaDefectoVideos;
import clases.ListaDefectoVideosEliminados;
import clases.ListaParticularVideos;
import clases.ListaParticularVideosEliminados;
import clases.ListaParticulares;
import clases.ListaParticularesEliminadas;
import clases.ListaporDefecto;
import clases.ListaporDefectoEliminadas;
import clases.Valorar;
import clases.UsuarioEliminado;
import excepciones.CanalRepetidoException;
import excepciones.EmailRepetidoException;
import excepciones.ValoracionException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import swing.MenuInicio;

/**
 *
 * @author tecnologo
 */
@WebService(endpointInterface = "interfaces.IControladorUsuario")
public class ControladorUsuario implements IControladorUsuario {

    @Override
    public void registrarUsuario(DtUsuario u, BufferedImage imagen) throws UsuarioRepetidoException, CanalRepetidoException, EmailRepetidoException {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario user = mu.buscadorUsuario(u.getNickname());
        if (user != null) {
            throw new UsuarioRepetidoException("El usuario ya existe");
        }
        user = mu.buscadorEmail(u.getEmail());
        if (user != null) {
            throw new EmailRepetidoException("El email ya existe");
        }
        Canal canal = mu.buscadorCanal(u.getCanal().getNombre_canal());
        if (canal != null) {
            throw new CanalRepetidoException("El canal ya existe");
        }
        user = new Usuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), new Canal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado()));
        mu.registrarUser(user);
        List<Usuario> users = mu.ObtenerUsuarios();
        if (users != null) {
            Usuario aux = (Usuario) users.get(users.size() - 1);
            List listasDefecto = mu.obtenerListasDefecto(aux.getNickname());
            boolean existe = false;
            for (int i = 0; i < listasDefecto.size(); i++) {
                ListaporDefecto listaAux = (ListaporDefecto) listasDefecto.get(i);
                if (listaAux.getNombreLista().equals("Historial")) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                ListaporDefecto historial = new ListaporDefecto("Historial", user.getCanal());
                listasDefecto.add(historial);
            }
            mu.agregarListasDefecto(listasDefecto, user.getNickname());

        }
        if (imagen != null) {
            
            String usuarioLogueado = System.getProperty("user.name");
            
            String path = "/home/"+usuarioLogueado+"/.UyTube/imagenesUsuarios/" + u.getNickname() + ".png";
            File file = new File(path);
            try {
                ImageIO.write(imagen, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List listaUsuarios() {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        List lista = mu.ObtenerUsuarios();
        if (lista != null) {
            List aux = new LinkedList();
            DtUsuario dtaux;
            for (int x = 0; x < lista.size(); x++) {
                dtaux = new DtUsuario((Usuario) lista.get(x));
                aux.add(dtaux);
            }
            return aux;
        }
        return null;
    }
    
    @Override
    public List listaUsuariosEliminados() {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        List lista = mu.ObtenerUsuariosEliminados();
        if (lista != null) {
            List aux = new LinkedList();
            DtUsuarioEliminado dtaux;
            for (int x = 0; x < lista.size(); x++) {
                dtaux = new DtUsuarioEliminado((UsuarioEliminado) lista.get(x));
                aux.add(dtaux);
            }
            return aux;
        }
        return null;
    }

    @Override
    public void modificarUsuario(DtUsuario u, DtCanal c, BufferedImage imagen, Boolean cambio) throws CanalRepetidoException {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();

        Canal canal = new Canal(c.getNombre_canal(), c.getDescripcion(), c.getPrivado());

        Canal CanalViejo = new Canal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado());

        Usuario us = new Usuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), CanalViejo);
        try {
            mu.modificarUsuario(us, canal);
        } catch (Exception e) {
            throw new CanalRepetidoException("Canal repetido");
        }
        if (cambio) {
            String usuarioLogueado = System.getProperty("user.name");
            String ruta = "/home/"+usuarioLogueado+"/.UyTube/imagenesUsuarios/" + u.getNickname() + ".png";
            Path path = Paths.get(ruta);
            try {
                File file = new File(ruta);
                if (file.exists() && !file.isDirectory()) {
                    Files.delete(path);
                }
                if (!u.getImagen().equals("/imagenesUsuarios/Defecto.png") && imagen != null) {
                    try {
                        ImageIO.write(imagen, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MenuInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void inicioBase() {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        mu.iniciarConexionInicial();
    }

    @Override
    public DtUsuario buscarUsuario(String nick) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario u;
        u = mu.buscadorUsuario(nick);
        if (u != null) {
            DtCanal canal = new DtCanal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado());
            DtUsuario user = new DtUsuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), canal);
            return user;
        }
        return null;
    }
    
    @Override
    public DtCanalEliminado buscarUsuarioEliminado(Integer id) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        CanalEliminado u = mu.buscadorUsuarioEliminado(id);
        if (u != null) {
            DtUsuarioEliminado us = new DtUsuarioEliminado(u.getId());
            DtCanalEliminado canal = new DtCanalEliminado(us, u.getNombre_canal(), u.getDescripcion(), u.getPrivado());
            return canal;
        }
        return null;
    }

    @Override
    public boolean buscarEmail(String email) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario u = mu.buscadorEmail(email);
        return u != null;
    }

    @Override
    public void seguirUsuario(DtUsuario u, String s) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Canal canal = new Canal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado());
        Usuario user = new Usuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), canal);
        mu.seguirUsuario(user, s);
    }

    @Override
    public void DejarSeguir_Usuario(DtUsuario u, String s) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Canal canal = new Canal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado());
        Usuario user = new Usuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), canal);
        mu.dejarSeguir(user, s);
    }

    @Override
    public List listaSeguidos(String nick) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        List lista = mu.ObtenerSeguidos(nick);
        return lista;
    }

    @Override
    public List listaSeguidores(String nick) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        List lista = mu.ObtenerSeguidores(nick);
        if (lista != null) {
            List aux = new LinkedList();
            DtUsuario dtaux;
            for (int x = 0; x < lista.size(); x++) {
                dtaux = new DtUsuario((Usuario) lista.get(x));
                aux.add(dtaux);
            }
            return aux;
        }
        return null;
    }

    @Override
    public void valorarVideo(String u, String video, String user_val, String val) throws ValoracionException {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario user = mu.buscadorUsuario(u);
        List lista = mu.obtenerVal(u);
        Valorar v = new Valorar(user, video, user_val, val);
        if (!lista.isEmpty()) {
            AuxiliarValorar dtaux;
            Boolean b = false;

            for (int x = 0; x < lista.size(); x++) {
                dtaux = (AuxiliarValorar) lista.get(x);
                if (dtaux.getVid().equals(video) && dtaux.getUser().equals(user_val) && !dtaux.getVal().equals(val)) {
                    try {
                        mu.modificoVal(v);
                    } catch (Exception e) {
                        throw new ValoracionException("Este usuario ya tiene la valoracion '" + v.getVal() + "' en este video");
                    }
                    b = true;
                }
            }
            if (b == false)
                try {
                mu.modificoVal(v);
            } catch (Exception e) {
                throw new ValoracionException("Este usuario ya tiene la valoracion '" + v.getVal() + "' en este video");
            }
        } else {
            try {
                mu.modificoVal(v);
            } catch (Exception e) {
                throw new ValoracionException("Este usuario ya tiene la valoracion '" + v.getVal() + "' en este video");
            }
        }
    }

    @Override
    public DtUsuario buscarUsuarioCanal(String c) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario u = mu.buscarUser2(c);
        if (u != null) {
            DtCanal canal = new DtCanal(u.getCanal().getNombre_canal(), u.getCanal().getDescripcion(), u.getCanal().getPrivado());
            DtUsuario user = new DtUsuario(u.getNickname(), u.getContrasenia(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFechaNac(), u.getImagen(), canal);
            return user;
        }
        return null;
    }

    @Override
    public void valorarVideoEliminar(String u, String video, String user_val, String val) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        Usuario user = mu.buscadorUsuario(u);
        Valorar v = new Valorar(user, video, user_val, val);
        mu.borrarValoracion(v);
    }

    @Override
    public void eliminarUsuario(DtUsuario user) {
        ManejadorInformacion mu = ManejadorInformacion.getInstance();
        UsuarioEliminado us = new UsuarioEliminado(user.getNickname(),user.getNombre(), user.getApellido(), user.getEmail(), user.getFechaNac(), user.getCanal().getNombre_canal());
        CanalEliminado ca = new CanalEliminado(us,us.getCanal(),user.getCanal().getDescripcion(),user.getCanal().getPrivado());
    
        List particulares = mu.obtenerListasParticulares(user.getNickname());
        List defecto = mu.obtenerListasDefecto(user.getNickname());
        List videos = mu.listaVid(new Canal(user.getCanal()));
      
        mu.guardarUser(us, ca, videos);
        
        ListaParticulares auxlp;
        ListaParticularesEliminadas listap;
        ListaParticularVideos auxv;
        ListaParticularVideosEliminados listavid;
        if (!particulares.isEmpty()) {
             for (int x = 0; x <= particulares.size() - 1; x++) {
                if (particulares.get(x) != null) {
                    auxlp = (ListaParticulares) particulares.get(x);
                    listap = new ListaParticularesEliminadas(us,auxlp.getNombreLista(),auxlp.getCategoria().getNombreCategoria(),auxlp.getPrivado());
                    mu.guardarParticular(listap);
                    
                    List videosLista = mu.getVideosListaParticular(auxlp.getId());
                    if(!videosLista.isEmpty()){
                        for (int y = 0; y <= videosLista.size() - 1; y++) {
                            if (videosLista.get(y) != null) {
                                 auxv = (ListaParticularVideos) videosLista.get(y);
                                 listavid = new ListaParticularVideosEliminados(listap,auxv.getVideo());
                                 mu.guardarVideosParticular(listavid);
                            }
                        }
                    }
                }
            }
        }
        
        ListaporDefecto auxld;
        ListaporDefectoEliminadas listad;
        ListaDefectoVideos auxvd;
        ListaDefectoVideosEliminados listavi;
        if (!defecto.isEmpty()) {
             for (int x = 0; x <= defecto.size() - 1; x++) {
                if (defecto.get(x) != null) {
                    auxld = (ListaporDefecto) defecto.get(x);
                    listad = new ListaporDefectoEliminadas(us,auxld.getNombreLista());
                    mu.guardarDefecto(listad);
                    
                    List videosLista = mu.getVideosListaDefecto(auxld.getId());
                    if(!videosLista.isEmpty()){
                        for (int y = 0; y <= videosLista.size() - 1; y++) {
                            if (videosLista.get(y) != null) {
                                 auxvd = (ListaDefectoVideos) videosLista.get(y);
                                 listavi = new  ListaDefectoVideosEliminados(listad,auxvd.getVideo());
                                 mu.guardarVideosDefecto(listavi);
                            }
                        }
                    }
                }
            }
        }
        mu.BorrarUser(user.getNickname());
    }

    @Override
    public void uploadImage(String fileName, byte[] imageBytes) {

        FileOutputStream fos = null;
        try {
            String user = System.getProperty("user.name");
            
            String filePath = "/home/"+user+"/.UyTube/imagenesUsuarios/" + fileName;
            fos = new FileOutputStream(filePath);
            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
            outputStream.write(imageBytes);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public byte[] downloadImage(String fileName) {
        String user = System.getProperty("user.name");
        String filePath = "/home/"+user+"/.UyTube/imagenesUsuarios/" + fileName;
        System.out.println("Sending file: " + filePath);
         
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            inputStream.close();
             
            return fileBytes;
        } catch (IOException ex) {
            System.err.println(ex);
            throw new WebServiceException(ex);
        }       
    }
}
