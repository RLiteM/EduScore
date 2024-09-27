/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Grado;
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
public class CSeccion {
    public static List<Seccion> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Seccion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Seccion.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idSeccion"))
                    .add(Projections.property("nombreSeccion"))
                    .add(Projections.property("idGrado"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
   public static boolean crearSeccion(int idSeccion, String nombreSeccion, int idGrado) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        // Verificar si ya existe una sección con el mismo idSeccion, nombre o idGrado
        Criteria criteria = session.createCriteria(Seccion.class);
        criteria.add(Restrictions.eq("idSeccion", idSeccion));
        criteria.add(Restrictions.eq("nombreSeccion", nombreSeccion));
        criteria.add(Restrictions.eq("idGrado", idGrado));
        criteria.add(Restrictions.eq("borradoLogico", true));

        Seccion existente = (Seccion) criteria.uniqueResult();

        if (existente == null) {
            // Si no existe una sección con el mismo idSeccion o nombre, crear una nueva
            Seccion nuevaSeccion = new Seccion();
            nuevaSeccion.setIdSeccion(idSeccion);
            nuevaSeccion.setNombreSeccion(nombreSeccion);

            // Obtener el grado al que pertenece la sección
            Grado grado = (Grado) session.get(Grado.class, idGrado);
            if (grado == null) {
                throw new RuntimeException("El grado " + idGrado + " no existe.");
            }

            // Asignar el grado a la sección
            nuevaSeccion.setGrado(grado);
            nuevaSeccion.setBorradoLogico(true);

            // Guardar la nueva sección
            session.save(nuevaSeccion);
            flag = true;
        } else {
            System.out.println("Ya existe una sección con el ID " + idSeccion + " o con el nombre " + nombreSeccion + " en el grado " + idGrado);
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

    public static boolean actualizarSeccion(int idSeccion, String nombreSeccion, int idGrado) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Seccion.class);
    criteria.add(Restrictions.eq("borradoLogico", true));
    Seccion actualizar = (Seccion) criteria.uniqueResult();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();
        if (actualizar == null) {

            //Actualizar atributos de la sección
            actualizar.setNombreSeccion(nombreSeccion);
            
            // Obtener el objeto Grado
            Grado grado = (Grado) session.get(Grado.class, idGrado);
            if (grado == null) {
                throw new RuntimeException("El grado " + idGrado + " no existe.");
            }

            // Asignar el grado a la sección
            actualizar.setGrado(grado);
            
            // Guardar la sección actualizada
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
    
     public static boolean anularSeccion(int idSeccion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Seccion.class);
        criteria.add(Restrictions.eq("idSeccion", idSeccion)); // Buscar sección por idSeccion
        Seccion anular = (Seccion) criteria.uniqueResult(); // obtener la sección existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontró la sección con el ID: " + idSeccion);
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

    public static boolean reactivarSeccion(int idSeccion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Seccion.class);
        criteria.add(Restrictions.eq("idSeccion", idSeccion)); // Buscar la sección por ID
        Seccion reactivar = (Seccion) criteria.uniqueResult(); // Obtener la sección existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (reactivar != null && !reactivar.getBorradoLogico()) { // Verificar si está anulada
                reactivar.setBorradoLogico(true); // Reactivar la sección
                session.update(reactivar);
                flag = true;
            } else if (reactivar == null) {
                System.out.println("No se encontró la sección con el ID: " + idSeccion);
            } else {
                System.out.println("La sección ya está activa.");
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