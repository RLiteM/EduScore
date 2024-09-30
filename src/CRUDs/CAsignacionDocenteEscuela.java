package CRUDs;

import POJOs.AsignacionDocenteEscuela;
import POJOs.Docente;
import POJOs.Escuela;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CAsignacionDocenteEscuela {

    // Método para listar todas las asignaciones de docentes a escuelas
    public static List<AsignacionDocenteEscuela> listarAsignaciones() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<AsignacionDocenteEscuela> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(AsignacionDocenteEscuela.class);
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idAsignacion"))
                    .add(Projections.property("docente.idDocente"))
                    .add(Projections.property("escuela.codigoEscuela"))
            );
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear una nueva asignación de docente a una escuela
    public static boolean crearAsignacion(int idDocente, String codigoEscuela) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe una asignación del docente a la escuela
            Criteria criteria = session.createCriteria(AsignacionDocenteEscuela.class);
            criteria.add(Restrictions.eq("docente.idDocente", idDocente));
            criteria.add(Restrictions.eq("escuela.codigoEscuela", codigoEscuela));

            AsignacionDocenteEscuela existente = (AsignacionDocenteEscuela) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe la asignación, crear una nueva
                AsignacionDocenteEscuela nuevaAsignacion = new AsignacionDocenteEscuela();

                // Obtener el docente y la escuela relacionados
                Docente docente = (Docente) session.get(Docente.class, idDocente);
                Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);

                if (docente == null || escuela == null) {
                    throw new RuntimeException("Docente o Escuela no existen.");
                }

                nuevaAsignacion.setDocente(docente);
                nuevaAsignacion.setEscuela(escuela);

                // Guardar la nueva asignación
                session.save(nuevaAsignacion);
                flag = true;
            } else {
                System.out.println("Ya existe una asignación del docente a la escuela especificada.");
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

    // Método para actualizar una asignación existente
    public static boolean actualizarAsignacion(int idAsignacion, int idDocente, String codigoEscuela) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            AsignacionDocenteEscuela asignacion = (AsignacionDocenteEscuela) session.get(AsignacionDocenteEscuela.class, idAsignacion);
            if (asignacion != null) {
                // Actualizar los datos de la asignación
                Docente docente = (Docente) session.get(Docente.class, idDocente);
                Escuela escuela = (Escuela) session.get(Escuela.class, codigoEscuela);

                if (docente == null || escuela == null) {
                    throw new RuntimeException("Docente o Escuela no existen.");
                }

                asignacion.setDocente(docente);
                asignacion.setEscuela(escuela);

                // Guardar la asignación actualizada
                session.update(asignacion);
                flag = true;
            } else {
                System.out.println("No se encontró la asignación.");
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

    // Método para eliminar una asignación
    public static boolean eliminarAsignacion(int idAsignacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            AsignacionDocenteEscuela asignacion = (AsignacionDocenteEscuela) session.get(AsignacionDocenteEscuela.class, idAsignacion);
            if (asignacion != null) {
                session.delete(asignacion);
                flag = true;
            } else {
                System.out.println("No se encontró la asignación.");
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
