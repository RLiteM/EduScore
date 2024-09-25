/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.CicloEscolar;
import POJOs.Escuela;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author wissegt
 */
public class CCicloEscolar {
    public static List<CicloEscolar> universo() {

        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<CicloEscolar> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CicloEscolar.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idCiclo"))
                    .add(Projections.property("escuela"))
                    .add(Projections.property("anio"))
                    .add(Projections.property("estado"))
                    .add(Projections.property("grados"))
            );

        } catch (Exception e) {
            System.out.println("error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
   public static boolean crear(int anio, String estado, String codigoEscuela) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();

        // Verificar si ya existe un ciclo escolar con el mismo año y estado 'Activo' en la misma escuela
        Criteria criteria = session.createCriteria(CicloEscolar.class);
        criteria.createAlias("escuela", "escuela");
        criteria.add(Restrictions.eq("anio", anio));
        criteria.add(Restrictions.eq("escuela.codigoEscuela", codigoEscuela));
        criteria.add(Restrictions.eq("estado", "Activo"));  // Verificar que el ciclo actual esté activo

        CicloEscolar existente = (CicloEscolar) criteria.uniqueResult();

        if (existente == null) {
            // Si no existe un ciclo activo, crear uno nuevo
            CicloEscolar nuevoCiclo = new CicloEscolar();
            nuevoCiclo.setAnio(anio);
            nuevoCiclo.setEstado(estado);

            // Obtener la escuela a la que pertenece el ciclo
            Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);
            if (escuela == null) {
                throw new RuntimeException("La escuela con código " + codigoEscuela + " no existe.");
            }

            // Asignar la escuela al ciclo escolar
            nuevoCiclo.setEscuela(escuela);
            nuevoCiclo.setBorradoLogico(true);

            // Guardar el nuevo ciclo escolar
            session.save(nuevoCiclo);
            flag = true;
        } else {
            System.out.println("Ya existe un ciclo escolar activo para el año " + anio + " en la escuela con código " + codigoEscuela);
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

    
    public static boolean actualizar( int idCiclo, int anio, String estado, String codigoEscuela) {
    boolean flag = false;
    Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
    Criteria criteria = session.createCriteria(CicloEscolar.class);
    criteria.add(Restrictions.eq("borradoLogico", true));
    CicloEscolar actualizar = (CicloEscolar) criteria.uniqueResult();
    Transaction transaction = null;
    try {
        transaction = session.beginTransaction();
        if (actualizar == null) {

            //atributos ciclo
            actualizar.setAnio(anio);
            actualizar.setEstado(estado);
            // Obtener el objeto Escuela
            Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);
            if (escuela == null) {
                throw new RuntimeException("La escuela con código " + codigoEscuela + " no existe.");
            }

            // Asignar la escuela al director
            actualizar.setEscuela(escuela);
            
 

            // Guardar el nuevo director
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
    
     public static boolean anular(int idCiclo) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(CicloEscolar.class);
        criteria.add(Restrictions.eq("idCiclo", idCiclo)); // corrigir datos escuela
        CicloEscolar anular = (CicloEscolar) criteria.uniqueResult(); // obtener la persona existente 
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (anular != null) {
                anular.setBorradoLogico(false);
                session.update(anular);
                flag = true;
            } else {
                System.out.println("No se encontro el codigo del Ciclo " + idCiclo);
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

    public static boolean reactivar(int idCiclo) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(CicloEscolar.class);
        criteria.add(Restrictions.eq("idCiclo", idCiclo)); // buscar la escuela por código
        CicloEscolar reactivar = (CicloEscolar) criteria.uniqueResult(); // obtener el Director existente
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (reactivar != null && !reactivar.getBorradoLogico()) { // verificar si está anulada
                reactivar.setBorradoLogico(true); // reactivar la escuela
                session.update(reactivar);
                flag = true;
            } else if (reactivar == null) {
                System.out.println("No se encontró el Ciclo con el código: " + idCiclo);
            } else {
                System.out.println("el Ciclo ya está activo.");
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
