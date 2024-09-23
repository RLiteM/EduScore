/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import POJOs.Director;
import POJOs.Escuela;
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
public class CDirector {

    public static List<Director> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Director> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Director.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idDirector"))
                    .add(Projections.property("escuela"))
                    .add(Projections.property("nombre"))
                    .add(Projections.property("apellido"))
                    .add(Projections.property("telefono"))
                    .add(Projections.property("contrasenia"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
// metodo para crear Director
public static boolean crear( String codigoEscuela, String nombre, String apellido, String telefono, String contrasenia) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Director.class);
    criteria.add(Restrictions.eq("borradoLogico", true));
    Director insert = (Director) criteria.uniqueResult();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();
        if (insert == null) {
            insert = new Director();
            // Obtener el objeto Escuela
            Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);
            if (escuela == null) {
                throw new RuntimeException("La escuela con código " + codigoEscuela + " no existe.");
            }

            // Asignar la escuela al director
            insert.setEscuela(escuela);
            
            // Asignar los demás atributos del director
            insert.setNombre(nombre);
            insert.setApellido(apellido);
            insert.setTelefono(telefono);
            insert.setContrasenia(contrasenia);
            insert.setBorradoLogico(true);

            // Guardar el nuevo director
            session.save(insert);
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

    

    // este es nuestro metodo actualizar, 
  public static boolean actualizar(int idDirector, String codigoEscuela, String nombre, String apellido, String telefono, String contrasenia) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(Director.class);
    criteria.add(Restrictions.eq("idDirector", idDirector)); // Buscar el director por su ID
    Director actualizar = (Director) criteria.uniqueResult(); // Obtener el director existente
    Transaction transaction = null;
    
    try {
        transaction = session.beginTransaction();
        
        if (actualizar != null) {
            // Obtener el objeto Escuela por su código
            Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);
            if (escuela == null) {
                throw new RuntimeException("La escuela con código " + codigoEscuela + " no existe.");
            }

            // Asignar la escuela al director
            actualizar.setEscuela(escuela);

            // Actualizar otros atributos
            actualizar.setNombre(nombre);
            actualizar.setApellido(apellido);
            actualizar.setTelefono(telefono);
            actualizar.setContrasenia(contrasenia);

            // Guardar los cambios en la base de datos
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


    public static boolean anular(int idDirector) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Director.class);
        criteria.add(Restrictions.eq("idDirector", idDirector)); // corrigir datos escuela
        Director anular = (Director) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontro el codigo del Director " + idDirector);
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

    public static boolean reactivar(int idDirector) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Director.class);
        criteria.add(Restrictions.eq("idDirector", idDirector)); // buscar la escuela por código
        Director reactivar = (Director) criteria.uniqueResult(); // obtener el Director existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (reactivar != null && !reactivar.getBorradoLogico()) { // verificar si está anulada
                reactivar.setBorradoLogico(true); // reactivar la escuela
                session.update(reactivar);
                flag = true;
            } else if (reactivar == null) {
                System.out.println("No se encontró el director con el código: " + idDirector);
            } else {
                System.out.println("el Director ya está activo.");
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

