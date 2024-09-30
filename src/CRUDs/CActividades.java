package CRUDs;

import POJOs.Actividad;
import POJOs.Curso;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CActividades {

    // Método para listar todas las actividades activas
    public static List<Actividad> listarActividades() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Actividad> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Actividad.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idActividad"))
                    .add(Projections.property("nombreActividad"))
                    .add(Projections.property("tipo"))
                    .add(Projections.property("unidad"))
                    .add(Projections.property("curso.idCurso"))
            );
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear una nueva actividad
    public static boolean crearActividad(String nombreActividad, String tipo, int unidad, int idCurso) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe una actividad con el mismo nombre y curso
            Criteria criteria = session.createCriteria(Actividad.class);
            criteria.add(Restrictions.eq("nombreActividad", nombreActividad));
            criteria.add(Restrictions.eq("curso.idCurso", idCurso));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Actividad existente = (Actividad) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe la actividad, crear una nueva
                Actividad nuevaActividad = new Actividad();
                nuevaActividad.setNombreActividad(nombreActividad);
                nuevaActividad.setTipo(tipo);
                nuevaActividad.setUnidad(unidad);

                // Obtener el curso relacionado
                Curso curso = (Curso) session.get(Curso.class, idCurso);
                if (curso == null) {
                    throw new RuntimeException("El curso con ID " + idCurso + " no existe.");
                }

                nuevaActividad.setCurso(curso);
                nuevaActividad.setBorradoLogico(true);

                // Guardar la nueva actividad
                session.save(nuevaActividad);
                flag = true;
            } else {
                System.out.println("Ya existe una actividad con el nombre " + nombreActividad + " en el curso con ID " + idCurso);
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

    // Método para actualizar una actividad existente
    public static boolean actualizarActividad(int idActividad, String nombreActividad, String tipo, int unidad, int idCurso) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Actividad actividad = (Actividad) session.get(Actividad.class, idActividad);
            if (actividad != null && actividad.getBorradoLogico()) {
                // Actualizar los datos de la actividad
                actividad.setNombreActividad(nombreActividad);
                actividad.setTipo(tipo);
                actividad.setUnidad(unidad);

                // Obtener el curso relacionado
                Curso curso = (Curso) session.get(Curso.class, idCurso);
                if (curso == null) {
                    throw new RuntimeException("El curso con ID " + idCurso + " no existe.");
                }

                actividad.setCurso(curso);

                // Guardar la actividad actualizada
                session.update(actividad);
                flag = true;
            } else {
                System.out.println("No se encontró la actividad o está anulada.");
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

    // Método para anular una actividad (borrado lógico)
    public static boolean anularActividad(int idActividad) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Actividad actividad = (Actividad) session.get(Actividad.class, idActividad);
            if (actividad != null && actividad.getBorradoLogico()) {
                actividad.setBorradoLogico(false);
                session.update(actividad);
                flag = true;
            } else {
                System.out.println("No se encontró la actividad o ya está anulada.");
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

    // Método para reactivar una actividad anulada
    public static boolean reactivarActividad(int idActividad) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Actividad actividad = (Actividad) session.get(Actividad.class, idActividad);
            if (actividad != null && !actividad.getBorradoLogico()) {
                actividad.setBorradoLogico(true);
                session.update(actividad);
                flag = true;
            } else if (actividad == null) {
                System.out.println("No se encontró la actividad con el ID: " + idActividad);
            } else {
                System.out.println("La actividad ya está activa.");
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
