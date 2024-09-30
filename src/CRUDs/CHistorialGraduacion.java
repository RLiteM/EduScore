package CRUDs;

import POJOs.HistorialGraduacion;
import POJOs.Estudiante;
import POJOs.Grado;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CHistorialGraduacion {

    // Método para listar todos los historiales de graduación activos
    public static List<HistorialGraduacion> listarHistorialGraduacion() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<HistorialGraduacion> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(HistorialGraduacion.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un nuevo historial de graduación
    public static boolean crearHistorialGraduacion(int idEstudiante, int idGrado, int anio, boolean promovido) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un historial para el mismo estudiante y grado
            Criteria criteria = session.createCriteria(HistorialGraduacion.class);
            criteria.add(Restrictions.eq("estudiante.idEstudiante", idEstudiante));
            criteria.add(Restrictions.eq("grado.idGrado", idGrado));
            criteria.add(Restrictions.eq("anio", anio));
            criteria.add(Restrictions.eq("borradoLogico", true));

            HistorialGraduacion existente = (HistorialGraduacion) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe, crear un nuevo historial
                HistorialGraduacion nuevoHistorial = new HistorialGraduacion();

                // Obtener el estudiante y el grado relacionados
                Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);
                Grado grado = (Grado) session.get(Grado.class, idGrado);

                if (estudiante == null || grado == null) {
                    throw new RuntimeException("Estudiante o Grado no existen.");
                }

                nuevoHistorial.setEstudiante(estudiante);
                nuevoHistorial.setGrado(grado);
                nuevoHistorial.setAnio(anio);
                nuevoHistorial.setPromovido(promovido);
                nuevoHistorial.setBorradoLogico(true);

                // Guardar el nuevo historial
                session.save(nuevoHistorial);
                flag = true;
            } else {
                System.out.println("Ya existe un historial para este estudiante en el grado especificado.");
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

    // Método para actualizar un historial existente
    public static boolean actualizarHistorialGraduacion(int idHistorial, int idEstudiante, int idGrado, int anio, boolean promovido) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            HistorialGraduacion historial = (HistorialGraduacion) session.get(HistorialGraduacion.class, idHistorial);
            if (historial != null && historial.getBorradoLogico()) {
                // Obtener el estudiante y el grado relacionados
                Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);
                Grado grado = (Grado) session.get(Grado.class, idGrado);

                if (estudiante == null || grado == null) {
                    throw new RuntimeException("Estudiante o Grado no existen.");
                }

                // Actualizar los datos del historial
                historial.setEstudiante(estudiante);
                historial.setGrado(grado);
                historial.setAnio(anio);
                historial.setPromovido(promovido);

                // Guardar el historial actualizado
                session.update(historial);
                flag = true;
            } else {
                System.out.println("No se encontró el historial o está anulado.");
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

    // Método para anular un historial (borrado lógico)
    public static boolean anularHistorialGraduacion(int idHistorial) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            HistorialGraduacion historial = (HistorialGraduacion) session.get(HistorialGraduacion.class, idHistorial);
            if (historial != null && historial.getBorradoLogico()) {
                historial.setBorradoLogico(false);
                session.update(historial);
                flag = true;
            } else {
                System.out.println("No se encontró el historial o ya está anulado.");
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

    // Método para reactivar un historial anulado
    public static boolean reactivarHistorialGraduacion(int idHistorial) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            HistorialGraduacion historial = (HistorialGraduacion) session.get(HistorialGraduacion.class, idHistorial);
            if (historial != null && !historial.getBorradoLogico()) {
                historial.setBorradoLogico(true);
                session.update(historial);
                flag = true;
            } else if (historial == null) {
                System.out.println("No se encontró el historial con el ID: " + idHistorial);
            } else {
                System.out.println("El historial ya está activo.");
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
