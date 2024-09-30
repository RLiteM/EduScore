package CRUDs;

import POJOs.Estudiante;
import POJOs.Seccion;
import POJOs.CicloEscolar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CEstudiante {

    // Método para listar todos los estudiantes activos
    public static List<Estudiante> listarEstudiantes() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Estudiante> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Estudiante.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un nuevo estudiante
    public static boolean crearEstudiante(long cui, String codigoEstudiante, String nombreEstudiante, int idSeccion, int idCicloEscolar) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un estudiante con el mismo CUI o código
            Criteria criteria = session.createCriteria(Estudiante.class);
            criteria.add(Restrictions.eq("cui", cui));
            criteria.add(Restrictions.eq("codigoEstudiante", codigoEstudiante));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Estudiante existente = (Estudiante) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe, crear un nuevo estudiante
                Estudiante nuevoEstudiante = new Estudiante();
                nuevoEstudiante.setCui(cui);
                nuevoEstudiante.setCodigoEstudiante(codigoEstudiante);
                nuevoEstudiante.setNombreEstudiante(nombreEstudiante);

                // Obtener la sección relacionada
                Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);
                if (seccion == null) {
                    throw new RuntimeException("La sección con ID " + idSeccion + " no existe.");
                }
                nuevoEstudiante.setSeccion(seccion);

                // Obtener el ciclo escolar relacionado
                CicloEscolar cicloEscolar = (CicloEscolar) session.get(CicloEscolar.class, idCicloEscolar);
                if (cicloEscolar == null) {
                    throw new RuntimeException("El ciclo escolar con ID " + idCicloEscolar + " no existe.");
                }
                nuevoEstudiante.setCicloEscolar(cicloEscolar);

                nuevoEstudiante.setBorradoLogico(true);

                // Guardar el nuevo estudiante
                session.save(nuevoEstudiante);
                flag = true;
            } else {
                System.out.println("Ya existe un estudiante con el CUI o código especificado.");
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

    // Método para actualizar un estudiante existente
    public static boolean actualizarEstudiante(int idEstudiante, long cui, String codigoEstudiante, String nombreEstudiante, int idSeccion, int idCicloEscolar) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);
            if (estudiante != null && estudiante.getBorradoLogico()) {
                // Actualizar los datos del estudiante
                estudiante.setCui(cui);
                estudiante.setCodigoEstudiante(codigoEstudiante);
                estudiante.setNombreEstudiante(nombreEstudiante);

                // Obtener la sección relacionada
                Seccion seccion = (Seccion) session.get(Seccion.class, idSeccion);
                if (seccion == null) {
                    throw new RuntimeException("La sección con ID " + idSeccion + " no existe.");
                }
                estudiante.setSeccion(seccion);

                // Obtener el ciclo escolar relacionado
                CicloEscolar cicloEscolar = (CicloEscolar) session.get(CicloEscolar.class, idCicloEscolar);
                if (cicloEscolar == null) {
                    throw new RuntimeException("El ciclo escolar con ID " + idCicloEscolar + " no existe.");
                }
                estudiante.setCicloEscolar(cicloEscolar);

                // Guardar el estudiante actualizado
                session.update(estudiante);
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante o está anulado.");
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

    // Método para anular un estudiante (borrado lógico)
    public static boolean anularEstudiante(int idEstudiante) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);
            if (estudiante != null && estudiante.getBorradoLogico()) {
                estudiante.setBorradoLogico(false);
                session.update(estudiante);
                flag = true;
            } else {
                System.out.println("No se encontró el estudiante o ya está anulado.");
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

    // Método para reactivar un estudiante anulado
    public static boolean reactivarEstudiante(int idEstudiante) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Estudiante estudiante = (Estudiante) session.get(Estudiante.class, idEstudiante);
            if (estudiante != null && !estudiante.getBorradoLogico()) {
                estudiante.setBorradoLogico(true);
                session.update(estudiante);
                flag = true;
            } else if (estudiante == null) {
                System.out.println("No se encontró el estudiante con el ID: " + idEstudiante);
            } else {
                System.out.println("El estudiante ya está activo.");
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
