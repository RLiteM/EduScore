package CRUDs;

import POJOs.Docente;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CDocente {

    // Método para listar todos los docentes activos
    public static List<Docente> listarDocentes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Docente> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Docente.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un nuevo docente
    public static boolean crearDocente(String codigoPersonal, long cui, String contrasenia) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un docente con el mismo código personal o CUI
            Criteria criteria = session.createCriteria(Docente.class);
            criteria.add(Restrictions.eq("codigoPersonal", codigoPersonal));
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Docente existente = (Docente) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe, crear un nuevo docente
                Docente nuevoDocente = new Docente();
                nuevoDocente.setCodigoPersonal(codigoPersonal);
                nuevoDocente.setCui(cui);
                nuevoDocente.setContrasenia(contrasenia);
                nuevoDocente.setBorradoLogico(true);

                // Guardar el nuevo docente
                session.save(nuevoDocente);
                flag = true;
            } else {
                System.out.println("Ya existe un docente con el código personal o CUI especificado.");
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

    // Método para actualizar un docente existente
    public static boolean actualizarDocente(int idDocente, String codigoPersonal, long cui, String contrasenia) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Docente docente = (Docente) session.get(Docente.class, idDocente);
            if (docente != null && docente.getBorradoLogico()) {
                // Actualizar los datos del docente
                docente.setCodigoPersonal(codigoPersonal);
                docente.setCui(cui);
                docente.setContrasenia(contrasenia);

                // Guardar el docente actualizado
                session.update(docente);
                flag = true;
            } else {
                System.out.println("No se encontró el docente o está anulado.");
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

    // Método para anular un docente (borrado lógico)
    public static boolean anularDocente(int idDocente) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Docente docente = (Docente) session.get(Docente.class, idDocente);
            if (docente != null && docente.getBorradoLogico()) {
                docente.setBorradoLogico(false);
                session.update(docente);
                flag = true;
            } else {
                System.out.println("No se encontró el docente o ya está anulado.");
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

    // Método para reactivar un docente anulado
    public static boolean reactivarDocente(int idDocente) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Docente docente = (Docente) session.get(Docente.class, idDocente);
            if (docente != null && !docente.getBorradoLogico()) {
                docente.setBorradoLogico(true);
                session.update(docente);
                flag = true;
            } else if (docente == null) {
                System.out.println("No se encontró el docente con el ID: " + idDocente);
            } else {
                System.out.println("El docente ya está activo.");
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
