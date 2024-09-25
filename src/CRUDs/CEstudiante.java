/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Estudiante;
import POJOs.Seccion;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alonzo Morales
 */
public class CEstudiante {
    public static List<Estudiante> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Estudiante> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Estudiante.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idEstudiante"))
                    .add(Projections.property("cui"))
                    .add(Projections.property("codigoEstudiante"))
                    .add(Projections.property("nombreEstudiante"))
                    .add(Projections.property("idSeccion"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
   public static boolean crearEstudiante(long cui, String codigoEstudiante, String nombreEstudiante, int idSeccion) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        // Verificar si ya existe un estudiante con el mismo cui y seccion
        Criteria criteria = session.createCriteria(Estudiante.class);
        criteria.add(Restrictions.eq("cui", cui));
        criteria.add(Restrictions.eq("Seccion", idSeccion));
        criteria.add(Restrictions.eq("borradoLogico",true));

        Estudiante existente = (Estudiante) criteria.uniqueResult();

        if (existente == null) {
            // Si no existe un estudiante con el mismo cui, crear uno nuevo
            Estudiante nuevoEstudiante = new Estudiante();
            nuevoEstudiante.setCui(cui);
            nuevoEstudiante.setCodigoEstudiante(codigoEstudiante);
            nuevoEstudiante.setNombreEstudiante(nombreEstudiante);

            // Obtener la seccion a la que pertenece el estudiante
            Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);
            if (seccion == null) {
                throw new RuntimeException("La seccion " + idSeccion + " no existe.");
            }

            // Asignar la sección al estudiante
            nuevoEstudiante.setSeccion(seccion);
            nuevoEstudiante.setBorradoLogico(true);

            // Guardar el nuevo estudiante
            session.save(nuevoEstudiante);
            flag = true;
        } else {
            System.out.println("Ya existe un estudiante con el CUI " + cui + " en la sección con ID  " + idSeccion);
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

    
    public static boolean actualizar(int idEstudiante, long cui, String codigoEstudiante, String nombreEstudiante, int idSeccion) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Estudiante.class);
    criteria.add(Restrictions.eq("borradoLogico", true));
    Estudiante actualizar = (Estudiante) criteria.uniqueResult();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();
        if (actualizar == null) {

            //atributos estudiante
            actualizar.setCui(cui);
            actualizar.setCodigoEstudiante(codigoEstudiante);
            actualizar.setNombreEstudiante(nombreEstudiante);
            
            // Obtener el objeto Seccion
            Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);
            if (seccion == null) {
                throw new RuntimeException("La sección " + idSeccion + " no existe.");
            }

            // Asigna la sección al estudiante
            actualizar.setSeccion(seccion);
            

            // Guardar el estudiante actualizado
            session.update(actualizar);
            flag = true;
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
    
     public static boolean anular(int idEstudiante) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Estudiante.class);
        criteria.add(Restrictions.eq("idEstudiante", idEstudiante)); // corrigir datos del estudiante
        Estudiante anular = (Estudiante) criteria.uniqueResult(); // obtener el estudiante existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante con el ID: " + idEstudiante);
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

    public static boolean reactivar(int idEstudiante) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Estudiante.class);
        criteria.add(Restrictions.eq("idEstudiante", idEstudiante)); // buscar el estudiante por ID
        Estudiante reactivar = (Estudiante) criteria.uniqueResult(); // obtener el estudiante existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (reactivar != null && !reactivar.getBorradoLogico()) { // verificar si está anulada
                reactivar.setBorradoLogico(true); // reactivar el estudiante
                session.update(reactivar);
                flag = true;
            } else if (reactivar == null) {
                System.out.println("No se encontró el estudiante con el ID: " + idEstudiante);
            } else {
                System.out.println("El estudiante ya está activo.");
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
