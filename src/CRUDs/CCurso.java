package CRUDs;

import POJOs.Curso;
import POJOs.Grado;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CCurso {

    // Método para listar todos los cursos activos
    public static List<Curso> listarCursos() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Curso> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Curso.class);
            criteria.add(Restrictions.eq("borradoLogico", true));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idCurso"))
                    .add(Projections.property("nombreCurso"))
                    .add(Projections.property("grado.idGrado"))
            );
            lista = criteria.list();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un nuevo curso
    public static boolean crearCurso(String nombreCurso, int idGrado) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Verificar si ya existe un curso con el mismo nombre y grado
            Criteria criteria = session.createCriteria(Curso.class);
            criteria.add(Restrictions.eq("nombreCurso", nombreCurso));
            criteria.add(Restrictions.eq("grado.idGrado", idGrado));
            criteria.add(Restrictions.eq("borradoLogico", true));

            Curso existente = (Curso) criteria.uniqueResult();

            if (existente == null) {
                // Si no existe el curso, crear uno nuevo
                Curso nuevoCurso = new Curso();
                nuevoCurso.setNombreCurso(nombreCurso);

                // Obtener el grado relacionado
                Grado grado = (Grado) session.get(Grado.class, idGrado);
                if (grado == null) {
                    throw new RuntimeException("El grado con ID " + idGrado + " no existe.");
                }

                nuevoCurso.setGrado(grado);
                nuevoCurso.setBorradoLogico(true);

                // Guardar el nuevo curso
                session.save(nuevoCurso);
                flag = true;
            } else {
                System.out.println("Ya existe un curso con el nombre " + nombreCurso + " en el grado con ID " + idGrado);
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

    // Método para actualizar un curso existente
    public static boolean actualizarCurso(int idCurso, String nombreCurso, int idGrado) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Curso curso = (Curso) session.get(Curso.class, idCurso);
            if (curso != null && curso.getBorradoLogico()) {
                // Actualizar los datos del curso
                curso.setNombreCurso(nombreCurso);

                // Obtener el grado relacionado
                Grado grado = (Grado) session.get(Grado.class, idGrado);
                if (grado == null) {
                    throw new RuntimeException("El grado con ID " + idGrado + " no existe.");
                }

                curso.setGrado(grado);

                // Guardar el curso actualizado
                session.update(curso);
                flag = true;
            } else {
                System.out.println("No se encontró el curso o está anulado.");
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

    // Método para anular un curso (borrado lógico)
    public static boolean anularCurso(int idCurso) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Curso curso = (Curso) session.get(Curso.class, idCurso);
            if (curso != null && curso.getBorradoLogico()) {
                curso.setBorradoLogico(false);
                session.update(curso);
                flag = true;
            } else {
                System.out.println("No se encontró el curso o ya está anulado.");
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

    // Método para reactivar un curso anulado
    public static boolean reactivarCurso(int idCurso) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Curso curso = (Curso) session.get(Curso.class, idCurso);
            if (curso != null && !curso.getBorradoLogico()) {
                curso.setBorradoLogico(true);
                session.update(curso);
                flag = true;
            } else if (curso == null) {
                System.out.println("No se encontró el curso con el ID: " + idCurso);
            } else {
                System.out.println("El curso ya está activo.");
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
