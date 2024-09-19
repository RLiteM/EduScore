/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;
import POJOs.Escuela;
import POJOs.Persona;
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
     public static List<Persona> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Persona> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Persona.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idPersona"))
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
    Persona insert = (Persona) criteria.uniqueResult();
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
public static boolean actualizar( String cui, String codigoPersonal, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String genero, String rol, String codigoEscuela) {
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



    
}