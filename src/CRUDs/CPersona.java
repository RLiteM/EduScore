/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
package CRUDs;

import POJOs.Escuela;
//import POJOs.Persona;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author IngeMayk
 */

public class CPersona {
/*
    public static List<Persona> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Persona> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Persona.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("cui"))
                    .add(Projections.property("codigoPersonal"))
                    .add(Projections.property("nombre"))
                    .add(Projections.property("apellido"))
                    .add(Projections.property("fechaNacimiento"))
                    .add(Projections.property("nacionalidad"))
                    .add(Projections.property("genero"))
                    .add(Projections.property("rol"))
                    .add(Projections.property("codigoEscuela"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean crear(String cui, String codigoPersonal, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String genero, String rol, String codigoEscuela) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Persona.class);
        criteria.add(Restrictions.eq("cui", cui));
        criteria.add(Restrictions.eq("borradoLogico", true));
     //   Persona insert = (Persona) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Persona();
                insert.setCui(cui);
                insert.setCodigoPersonal(codigoPersonal);
                insert.setNombre(nombre);
                insert.setApellido(apellido);

                // Convertir String a Date
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaNac = formatter.parse(fechaNacimiento);
                insert.setFechaNacimiento(fechaNac);  // Alonzo en nuestra base de datos, es de tipo date no string

                insert.setNacionalidad(nacionalidad);
                insert.setGenero(genero);
                insert.setRol(rol);

                // Aquí obtenemos la Escuela por su código
                Criteria escuelaCriteria = session.createCriteria(Escuela.class);
                escuelaCriteria.add(Restrictions.eq("codigoEscuela", codigoEscuela));
                Escuela escuela = (Escuela) escuelaCriteria.uniqueResult();  // Buscar la escuela por su código

                if (escuela != null) {
                    insert.setEscuela(escuela);  // Asignar la escuela
                } else {
                    System.out.println("Escuela no encontrada.");
                }

                insert.setBorradoLogico(true);
                session.save(insert);
                flag = true;
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    // este es nuestro metodo actualizar, 
    public static boolean actualizar(String cui, String codigoPersonal, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String genero, String rol, String codigoEscuela) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Persona.class);
        criteria.add(Restrictions.eq("cui", cui)); // Buscar la persona por cui
        Persona insert = (Persona) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert != null) {
                insert.setCui(cui);
                insert.setCodigoPersonal(codigoPersonal);
                insert.setNombre(nombre);
                insert.setApellido(apellido);

                // Convertir String a Date
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaNac = formatter.parse(fechaNacimiento);
                insert.setFechaNacimiento(fechaNac);  // alonzo usar el objeto Date

                insert.setNacionalidad(nacionalidad);
                insert.setGenero(genero);
                insert.setRol(rol);

                // Buscar la nueva escuela por su código
                Criteria escuelaCriteria = session.createCriteria(Escuela.class);
                escuelaCriteria.add(Restrictions.eq("codigoEscuela", codigoEscuela));
                Escuela escuela = (Escuela) escuelaCriteria.uniqueResult();

                if (escuela != null) {
                    insert.setEscuela(escuela);  // Asignar la escuela encontrada
                } else {
                    System.out.println("Escuela no encontrada.");
                }

                insert.setBorradoLogico(true);
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

    public static boolean anular(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Persona.class);
        criteria.add(Restrictions.eq("cui", cui)); // corrigir datos persona
        Persona anular = (Persona) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontro el empleado" + cui);
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

    public static boolean reactivar(String cui) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Persona.class);
        criteria.add(Restrictions.eq("cui", cui)); // buscar la empleado por cui
        Persona reactivar = (Persona) criteria.uniqueResult(); // obtener la empleado existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (reactivar != null && !reactivar.getBorradoLogico()) { // verificar si está anulado
                reactivar.setBorradoLogico(true); // reactivar el docente
                session.update(reactivar);
                flag = true;
            } else if (reactivar == null) {
                System.out.println("No se encontró la persona con el CUI: " + cui);
            } else {
                System.out.println("El emplado ya está activa.");
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
*/
}
