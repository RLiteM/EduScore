/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Escuela;
import POJOs.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author hygro
 */
public class CUsuario {
    public static List <Usuario> universo(){
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List <Usuario> lista=null;
        
        try {//Aqui ira un try catch
            session.beginTransaction();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("borradorLogico", true));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("idUsuario"))
                .add(Projections.property("nombre_usuario"))
                .add(Projections.property("contrasena"))
                .add(Projections.property("rol"))
                .add(Projections.property("codigo_escuela"))
                .add(Projections.property("id_grado")));
        
        } catch (Exception e) {
            System.out.println("error" + e);
    }finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
    public static boolean crear(String usu, String cont) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Escuela.class);
        criteria.add(Restrictions.eq("nombreUsuario", usu));
        criteria.add(Restrictions.eq("contrasena", cont));
        Escuela insert = (Escuela) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Escuela();
                insert.setCodigoEscuela(usu);
                insert.setNombre(cont);
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
}
