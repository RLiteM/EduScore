/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;
import POJOs.Persona;
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
                insert.setFechaNacimiento(fechaNacimiento);
                insert.setNacionalidad(nacionalidad);
                insert.setGenero(genero);
                insert.setRol(rol);
                insert.setCodigoEscuela(codigoEscuela);
                insert.setBorradoLogico(true);
                session.save(insert);
                flag = true;
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return flag;
    }
    // este es nuestro metodo actualizar, 
       public static boolean actualizar(int idPersona, String cui, String codigoPersonal, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String genero, String rol, String codigoEscuela) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Persona.class);
        criteria.add(Restrictions.eq("idPersona", idPersona)); // corrigir datos escuela
        Persona insert = (Persona) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert != null) {
                insert.setCui(cui);
                insert.setCodigoPersonal(codigoPersonal);
                insert.setNombre(nombre);
                insert.setApellido(apellido);
                insert.setFechaNacimiento(fechaNacimiento);
                insert.setNacionalidad(nacionalidad);
                insert.setGenero(genero);
                insert.setRol(rol);
                insert.setcodigoEscuela(codigoEscuela);
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
