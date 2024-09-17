package POJOs;
// Generated 16-sep-2024 21:46:47 by Hibernate Tools 4.3.1



/**
 * Cursodocente generated by hbm2java
 */
public class Cursodocente  implements java.io.Serializable {


     private int idCursoDocente;
     private Curso curso;
     private Persona persona;
     private Boolean borradoLogico;

    public Cursodocente() {
    }

	
    public Cursodocente(int idCursoDocente) {
        this.idCursoDocente = idCursoDocente;
    }
    public Cursodocente(int idCursoDocente, Curso curso, Persona persona, Boolean borradoLogico) {
       this.idCursoDocente = idCursoDocente;
       this.curso = curso;
       this.persona = persona;
       this.borradoLogico = borradoLogico;
    }
   
    public int getIdCursoDocente() {
        return this.idCursoDocente;
    }
    
    public void setIdCursoDocente(int idCursoDocente) {
        this.idCursoDocente = idCursoDocente;
    }
    public Curso getCurso() {
        return this.curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public Persona getPersona() {
        return this.persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public Boolean getBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(Boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }




}


