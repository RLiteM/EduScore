package POJOs;
// Generated 29-sep-2024 19:19:33 by Hibernate Tools 4.3.1



/**
 * AsignacionDocenteEscuela generated by hbm2java
 */
public class AsignacionDocenteEscuela  implements java.io.Serializable {


     private Integer idAsignacion;
     private Docente docente;
     private Escuela escuela;

    public AsignacionDocenteEscuela() {
    }

    public AsignacionDocenteEscuela(Docente docente, Escuela escuela) {
       this.docente = docente;
       this.escuela = escuela;
    }
   
    public Integer getIdAsignacion() {
        return this.idAsignacion;
    }
    
    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }
    public Docente getDocente() {
        return this.docente;
    }
    
    public void setDocente(Docente docente) {
        this.docente = docente;
    }
    public Escuela getEscuela() {
        return this.escuela;
    }
    
    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }




}


