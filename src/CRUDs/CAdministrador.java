/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Administrador;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
/**
 *
 * @author IngeMayk
 */
public class CAdministrador {
    // Método para obtener los usuarios activos (no borrados lógicamente)
    public static List<Administrador> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Administrador> lista = null;
        try {
            
            // Nos sirve para ver la informacion activa en cualquir tablla, 
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Administrador.class);
            criteria.add(Restrictions.eq("borradoLogico", true)); // Solo usuarios no eliminados
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idadministrador"))
                    .add(Projections.property("cui"))
                    .add(Projections.property("nombre"))
                    .add(Projections.property("apellido"))
                    .add(Projections.property("telefono"))
                    .add(Projections.property("contrasenia"))
            );
        } catch (Exception e) {
            System.out.println("Error al obtener el Administrador: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un Administrador
    public static boolean crearAdministrador(long cui, String nombre,String apellido, String telefono, String contrasenia) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Administrador.class);
        criteria.add(Restrictions.eq("cui", cui));
        criteria.add(Restrictions.eq("borradoLogico", true));
        Administrador insert = (Administrador) criteria.uniqueResult(); // Verificar si ya existe el usuario
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Administrador();
                insert.setCui(cui);
                insert.setNombre(nombre);
                insert.setApellido(apellido);
                insert.setTelefono(telefono);
                insert.setContrasenia(contrasenia);

                insert.setBorradoLogico(true);
                session.save(insert);
                flag = true;
            } else {
                System.out.println("El Administrador ya existe.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para actualizar un usuario
    public static boolean actualizarAdministrador(int idAdministrador, long cui, String nombre,String apellido, String telefono, String contrasenia) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Administrador.class);
        criteria.add(Restrictions.eq("idAdministrador", idAdministrador)); // Buscar por ID de usuario
        Administrador actualizar = (Administrador) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (actualizar != null) {
                actualizar.setCui(cui);
                actualizar.setNombre(nombre);
                actualizar.setApellido(apellido);
                actualizar.setTelefono(telefono);
                actualizar.setContrasenia(contrasenia);
                session.update(actualizar);
                flag = true;
            } else {
                System.out.println("No se encontró el Administrador con ID: " + idAdministrador);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para anular (borrar lógicamente) un usuario
    public static boolean anularAdministrador(int idAdministrador) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Administrador.class);
        criteria.add(Restrictions.eq("idAdministrador", idAdministrador));
        Administrador anular = (Administrador) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false); // Marcar como borrado lógicamente
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontró el Administrador con ID: " + idAdministrador);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // Método para reactivar un usuario (revertir borrado lógico)
// Método para reactivar un usuario (revertir borrado lógico)
// Método para reactivar un administrador (revertir borrado lógico)
public static boolean reactivarAdministrador(int idAdministrador) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Administrador.class);
    criteria.add(Restrictions.eq("idAdministrador", idAdministrador));
    Administrador reactivar = (Administrador) criteria.uniqueResult();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();
        if (reactivar != null && !reactivar.getBorradoLogico()) { // Si borradoLogico es false o 0
            reactivar.setBorradoLogico(true); // Reactivar el usuario (poner true o 1)
            session.update(reactivar);
            flag = true;
        } else if (reactivar == null) {
            System.out.println("No se encontró el Administrador con ID: " + idAdministrador);
        } else {
            System.out.println("El Administrador ya está activo.");
        }
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        session.close();
    }
    return flag;
}

}




    

