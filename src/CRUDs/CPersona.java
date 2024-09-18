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
                    .add(Projections.property("nombre"))
                    .add(Projections.property("apellido"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    public static boolean crear(String codigoEscuela, String nombre, String direccion, String telefono) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Escuela.class);
        criteria.add(Restrictions.eq("codigoEscuela", codigoEscuela));
        criteria.add(Restrictions.eq("borradoLogico", true));
        Escuela insert = (Escuela) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Escuela();
                insert.setCodigoEscuela(codigoEscuela);
                insert.setNombre(nombre);
                insert.setDireccion(direccion);
                insert.setTelefono(telefono);
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
        public static boolean actualizar(String codigoEscuela, String nombre, String direccion, String telefono) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Escuela.class);
        criteria.add(Restrictions.eq("codigoEscuela", codigoEscuela)); // corrigir datos escuela
        Escuela insert = (Escuela) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert != null) {
                insert.setNombre(nombre);
                insert.setDireccion(direccion);
                insert.setTelefono(telefono);
                session.update(insert);
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
