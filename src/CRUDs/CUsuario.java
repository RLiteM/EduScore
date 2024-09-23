package CRUDs;

import POJOs.Escuela;
/*import POJOs.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

/**
 * Clase para operaciones CRUD sobre la entidad Usuario
 */
public class CUsuario {
    /*

    // Método para obtener los usuarios activos (no borrados lógicamente)
    public static List<Usuario> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Usuario> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("borradoLogico", true)); // Solo usuarios no eliminados
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idUsuario"))
                    .add(Projections.property("nombreUsuario"))
                    .add(Projections.property("contrasena"))
                    .add(Projections.property("rol"))
                    .add(Projections.property("escuela"))
                    .add(Projections.property("rol"))
            );
        } catch (Exception e) {
            System.out.println("Error al obtener usuarios: " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    // Método para crear un usuario
    public static boolean crearUsuario(String nombreUsuario, String contrasena, String rol, String codigoEscuela, String codigoCurso) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));
        criteria.add(Restrictions.eq("borradoLogico", true));
        Usuario insert = (Usuario) criteria.uniqueResult(); // Verificar si ya existe el usuario
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Usuario();
                insert.setNombreUsuario(nombreUsuario);
                insert.setContrasena(contrasena);
                insert.setRol(rol);
                  // Aquí obtenemos la Escuela por su código
                Criteria escuelaCriteria = session.createCriteria(Escuela.class);
                escuelaCriteria.add(Restrictions.eq("codigoEscuela", codigoEscuela));
                Escuela escuela = (Escuela) escuelaCriteria.uniqueResult();  // Buscar la escuela por su código

                
                insert.setRol(rol);
                insert.setBorradoLogico(true);
                session.save(insert);
                flag = true;
            } else {
                System.out.println("El usuario ya existe.");
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

    // Método para actualizar un usuario
    public static boolean actualizarUsuario(int idUsuario, String nombreUsuario, String contrasena, String rol) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("idUsuario", idUsuario)); // Buscar por ID de usuario
        Usuario usuario = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (usuario != null) {
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setContrasena(contrasena);
                usuario.setRol(rol);
                session.update(usuario);
                flag = true;
            } else {
                System.out.println("No se encontró el usuario con ID: " + idUsuario);
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

    // Método para anular (borrar lógicamente) un usuario
    public static boolean anularUsuario(int idUsuario) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("idUsuario", idUsuario));
        Usuario usuario = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (usuario != null) {
                usuario.setBorradoLogico(true); // Marcar como borrado lógicamente
                session.update(usuario);
                flag = true;
            } else {
                System.out.println("No se encontró el usuario con ID: " + idUsuario);
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

    // Método para reactivar un usuario (revertir borrado lógico)
    public static boolean reactivarUsuario(int idUsuario) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("idUsuario", idUsuario));
        Usuario usuario = (Usuario) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (usuario != null && usuario.getBorradoLogico()) {
                usuario.setBorradoLogico(false); // Reactivar el usuario
                session.update(usuario);
                flag = true;
            } else if (usuario == null) {
                System.out.println("No se encontró el usuario con ID: " + idUsuario);
            } else {
                System.out.println("El usuario ya está activo.");
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
*/
}
