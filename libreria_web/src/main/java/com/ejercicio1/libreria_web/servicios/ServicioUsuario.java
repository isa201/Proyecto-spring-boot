/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.servicios;

import com.ejercicio1.libreria_web.Enum.Rol;
import com.ejercicio1.libreria_web.entidades.Usuario;
import com.ejercicio1.libreria_web.repositorios.UsuarioRepositorio;
import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author USER
 */
@Service
public class ServicioUsuario implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;

    @Transactional
    public void registrarUsuario(String nombre, String clave, Integer dni, String mail, boolean alta) throws MiExcepcion {

        try {
            validar(nombre, clave, dni, mail);
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);
            usuario.setDni(dni);
            usuario.setMail(mail);
            usuario.setAlta(alta);
            //todas las personas que se registren van a ser usuarios
            usuario.setRol(Rol.USUARIO);

            ur.save(usuario);
        } catch (MiExcepcion e) {
            throw e;
        }

    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String clave, Integer dni, String mail, boolean alta) throws MiExcepcion {

        try {
            validar(nombre, clave, dni, mail);
            Optional<Usuario> respuesta = ur.findById(id);
            if (respuesta.isPresent()) {
                Usuario usuario = respuesta.get();
                usuario.setNombre(nombre);
                usuario.setClave(clave);
                usuario.setDni(dni);
                usuario.setMail(mail);
                usuario.setAlta(alta);

                ur.save(usuario);
            } else {
                throw new MiExcepcion("No se encontro el usuario con ese id");
            }
        } catch (MiExcepcion e) {
            throw e;
        }

    }

    @Transactional
    public void eliminarUsuario(String id) {
        Usuario usuario = ur.buscarUsuarioPorId(id);
        ur.delete(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(String id) {
        return ur.buscarUsuarioPorId(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return ur.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = ur.buscarUsuarioPorMail(mail);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            //Creo una lista de permisos! 
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol()); //se le asigna autmaticamente el rol de usuario
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario); // llave + valor

            // crea un objeto tipo user con el mail y la clave del objeto usuario creado
            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }

    public void validar(String nombre, String clave, Integer dni, String mail) throws MiExcepcion {
        if (nombre.isEmpty()) {
            throw new MiExcepcion("Debe ingresar un nombre");
        }
        if (clave.isEmpty()) {
            throw new MiExcepcion("Debe ingresar una clave");
        }
        if (mail.isEmpty()) {
            throw new MiExcepcion("Debe ingresar un mail");
        }
        if (dni == null) {
            throw new MiExcepcion("Debe ingresar un dni");
        }
    }

}
