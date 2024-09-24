package POJOs;
// Generated 23/09/2024 12:41:05 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Seccion generated by hbm2java
 */
public class Seccion  implements java.io.Serializable {


     private Integer idSeccion;
     private Grado grado;
     private String nombreSeccion;
     private Boolean borradoLogico;
     private Set<AsignacionDocenteCurso> asignacionDocenteCursos = new HashSet<AsignacionDocenteCurso>(0);
     private Set<Estudiante> estudiantes = new HashSet<Estudiante>(0);

    public Seccion() {
    }

	
    public Seccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }
    public Seccion(Grado grado, String nombreSeccion, Boolean borradoLogico, Set<AsignacionDocenteCurso> asignacionDocenteCursos, Set<Estudiante> estudiantes) {
       this.grado = grado;
       this.nombreSeccion = nombreSeccion;
       this.borradoLogico = borradoLogico;
       this.asignacionDocenteCursos = asignacionDocenteCursos;
       this.estudiantes = estudiantes;
    }
   
    public Integer getIdSeccion() {
        return this.idSeccion;
    }
    
    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }
    public Grado getGrado() {
        return this.grado;
    }
    
    public void setGrado(Grado grado) {
        this.grado = grado;
    }
    public String getNombreSeccion() {
        return this.nombreSeccion;
    }
    
    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }
    public Boolean getBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(Boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }
    public Set<AsignacionDocenteCurso> getAsignacionDocenteCursos() {
        return this.asignacionDocenteCursos;
    }
    
    public void setAsignacionDocenteCursos(Set<AsignacionDocenteCurso> asignacionDocenteCursos) {
        this.asignacionDocenteCursos = asignacionDocenteCursos;
    }
    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }
    
    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }




}


