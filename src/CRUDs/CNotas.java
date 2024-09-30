package CRUDs;

import POJOs.Notas;
import POJOs.Actividad;
import POJOs.Estudiante;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CNotas {

    // Método para listar todas las notas activas
    public static List<Notas> listarNotas() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Notas> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Notas.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear una nueva nota
    public static boolean crearNota(int idActividad, int idEstudiante, double valorNota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe una nota para el mismo estudiante y actividad
            Criteria criteria = session.createCriteria(Notas.class);
            criteria.add(Restrictions.eq("actividad.idActividad", idActividad));
            criteria.add(Restrictions.eq("estudiante.idEstudiante", idEstudiante));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Notas existente = (Notas) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe la nota, crear una nueva
                Notas nuevaNota = new Notas();
                nuevaNota.setNota(BigDecimal.valueOf(valorNota));  // Conversión a BigDecimal

                // Obtener la actividad y el estudiante relacionados
                Actividad actividad = (Actividad) session.get(Actividad.class, idActividad);
                Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);

                if (actividad == null || estudiante == null) {
                    throw new RuntimeException("Actividad o Estudiante no existen.");
                }

                nuevaNota.setActividad(actividad);
                nuevaNota.setEstudiante(estudiante);
                nuevaNota.setBorradoLogico(true);

                // Guardar la nueva nota
                session.save(nuevaNota);
                flag = true;
            } else {
                System.out.println("Ya existe una nota para este estudiante en la actividad especificada.");
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

    // Método para actualizar una nota existente
    public static boolean actualizarNota(int idNota, double nuevaNota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Notas nota = (Notas) session.get(Notas.class, idNota);
            if (nota != null && nota.getBorradoLogico()) {
                // Actualizar el valor de la nota
                nota.setNota(BigDecimal.valueOf(nuevaNota));  // Conversión a BigDecimal

                // Guardar la nota actualizada
                session.update(nota);
                flag = true;
            } else {
                System.out.println("No se encontró la nota o está anulada.");
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

    // Método para anular una nota (borrado lógico)
    public static boolean anularNota(int idNota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Notas nota = (Notas) session.get(Notas.class, idNota);
            if (nota != null && nota.getBorradoLogico()) {
                nota.setBorradoLogico(false);
                session.update(nota);
                flag = true;
            } else {
                System.out.println("No se encontró la nota o ya está anulada.");
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

    // Método para reactivar una nota anulada
    public static boolean reactivarNota(int idNota) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Notas nota = (Notas) session.get(Notas.class, idNota);
            if (nota != null && !nota.getBorradoLogico()) {
                nota.setBorradoLogico(true);
                session.update(nota);
                flag = true;
            } else if (nota == null) {
                System.out.println("No se encontró la nota con el ID: " + idNota);
            } else {
                System.out.println("La nota ya está activa.");
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
