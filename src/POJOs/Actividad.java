package POJOs;
// Generated 22-sep-2024 18:38:34 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Actividad generated by hbm2java
 */
public class Actividad  implements java.io.Serializable {


     private int idActividad;
     private Curso curso;
     private String nombreActividad;
     private String tipo;
     private Integer unidad;
     private Boolean borradoLogico;
     private Set<Notas> notases = new HashSet<Notas>(0);

    public Actividad() {
    }

	
    public Actividad(int idActividad, String nombreActividad, String tipo) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.tipo = tipo;
    }
    public Actividad(int idActividad, Curso curso, String nombreActividad, String tipo, Integer unidad, Boolean borradoLogico, Set<Notas> notases) {
       this.idActividad = idActividad;
       this.curso = curso;
       this.nombreActividad = nombreActividad;
       this.tipo = tipo;
       this.unidad = unidad;
       this.borradoLogico = borradoLogico;
       this.notases = notases;
    }
   
    public int getIdActividad() {
        return this.idActividad;
    }
    
    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
    public Curso getCurso() {
        return this.curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public String getNombreActividad() {
        return this.nombreActividad;
    }
    
    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }
    public Boolean getBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(Boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }
    public Set<Notas> getNotases() {
        return this.notases;
    }
    
    public void setNotases(Set<Notas> notases) {
        this.notases = notases;
    }




}


