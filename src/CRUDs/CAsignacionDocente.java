package CRUDs;

import POJOs.AsignacionDocenteCurso;
import POJOs.Curso;
import POJOs.Docente;
import POJOs.Seccion;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CAsignacionDocente {

    // Método para listar todas las asignaciones activas
    public static List<AsignacionDocenteCurso> listarAsignaciones() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<AsignacionDocenteCurso> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(AsignacionDocenteCurso.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idAsignacion"))
                    .add(Projections.property("curso.idCurso"))
                    .add(Projections.property("docente.idDocente"))
                    .add(Projections.property("seccion.idSeccion"))
            );
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear una nueva asignación de docente a un curso
    public static boolean crearAsignacion(int idCurso, int idDocente, int idSeccion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe una asignación para el docente en el mismo curso y sección
            Criteria criteria = session.createCriteria(AsignacionDocenteCurso.class);
            criteria.add(Restrictions.eq("curso.idCurso", idCurso));
            criteria.add(Restrictions.eq("docente.idDocente", idDocente));
            criteria.add(Restrictions.eq("seccion.idSeccion", idSeccion));
            criteria.add(Restrictions.eq("borradoLogico", true));

            AsignacionDocenteCurso existente = (AsignacionDocenteCurso) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe la asignación, crear una nueva
                AsignacionDocenteCurso nuevaAsignacion = new AsignacionDocenteCurso();

                // Obtener el curso, docente y sección relacionados
                Curso curso = (Curso) session.get(Curso.class, idCurso);
                Docente docente = (Docente) session.get(Docente.class, idDocente);
                Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);

                if (curso == null || docente == null || seccion == null) {
                    throw new RuntimeException("Curso, Docente o Sección no existen.");
                }

                nuevaAsignacion.setCurso(curso);
                nuevaAsignacion.setDocente(docente);
                nuevaAsignacion.setSeccion(seccion);
                nuevaAsignacion.setBorradoLogico(true);

                // Guardar la nueva asignación
                session.save(nuevaAsignacion);
                flag = true;
            } else {
                System.out.println("Ya existe una asignación para el docente en el curso y sección especificados.");
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
    public static boolean actualizarAsignacion(int idAsignacion, int idCurso, int idDocente, int idSeccion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            AsignacionDocenteCurso asignacion = (AsignacionDocenteCurso) session.get(AsignacionDocenteCurso.class, idAsignacion);
            if (asignacion != null && asignacion.getBorradoLogico()) {
                // Actualizar los datos de la asignación
                Curso curso = (Curso) session.get(Curso.class, idCurso);
                Docente docente = (Docente) session.get(Docente.class, idDocente);
                Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);

                if (curso == null || docente == null || seccion == null) {
                    throw new RuntimeException("Curso, Docente o Sección no existen.");
                }

                asignacion.setCurso(curso);
                asignacion.setDocente(docente);
                asignacion.setSeccion(seccion);

                // Guardar la asignación actualizada
                session.update(asignacion);
                flag = true;
            } else {
                System.out.println("No se encontró la asignación o está anulada.");
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

    // Método para anular una asignación (borrado lógico)
    public static boolean anularAsignacion(int idAsignacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            AsignacionDocenteCurso asignacion = (AsignacionDocenteCurso) session.get(AsignacionDocenteCurso.class, idAsignacion);
            if (asignacion != null && asignacion.getBorradoLogico()) {
                asignacion.setBorradoLogico(false);
                session.update(asignacion);
                flag = true;
            } else {
                System.out.println("No se encontró la asignación o ya está anulada.");
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

    // Método para reactivar una asignación anulada
    public static boolean reactivarAsignacion(int idAsignacion) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            AsignacionDocenteCurso asignacion = (AsignacionDocenteCurso) session.get(AsignacionDocenteCurso.class, idAsignacion);
            if (asignacion != null && !asignacion.getBorradoLogico()) {
                asignacion.setBorradoLogico(true);
                session.update(asignacion);
                flag = true;
            } else if (asignacion == null) {
                System.out.println("No se encontró la asignación con el ID: " + idAsignacion);
            } else {
                System.out.println("La asignación ya está activa.");
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
