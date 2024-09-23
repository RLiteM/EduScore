package POJOs;
// Generated 22-sep-2024 18:38:34 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Estudiante generated by hbm2java
 */
public class Estudiante  implements java.io.Serializable {


     private Integer idEstudiante;
     private Seccion seccion;
     private long cui;
     private String codigoEstudiante;
     private String nombreEstudiante;
     private Boolean borradoLogico;
     private Set<Notas> notases = new HashSet<Notas>(0);
     private Set<HistorialGraduacion> historialGraduacions = new HashSet<HistorialGraduacion>(0);

    public Estudiante() {
    }

	
    public Estudiante(long cui, String codigoEstudiante, String nombreEstudiante) {
        this.cui = cui;
        this.codigoEstudiante = codigoEstudiante;
        this.nombreEstudiante = nombreEstudiante;
    }
    public Estudiante(Seccion seccion, long cui, String codigoEstudiante, String nombreEstudiante, Boolean borradoLogico, Set<Notas> notases, Set<HistorialGraduacion> historialGraduacions) {
       this.seccion = seccion;
       this.cui = cui;
       this.codigoEstudiante = codigoEstudiante;
       this.nombreEstudiante = nombreEstudiante;
       this.borradoLogico = borradoLogico;
       this.notases = notases;
       this.historialGraduacions = historialGraduacions;
    }
   
    public Integer getIdEstudiante() {
        return this.idEstudiante;
    }
    
    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    public Seccion getSeccion() {
        return this.seccion;
    }
    
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
    public long getCui() {
        return this.cui;
    }
    
    public void setCui(long cui) {
        this.cui = cui;
    }
    public String getCodigoEstudiante() {
        return this.codigoEstudiante;
    }
    
    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }
    public String getNombreEstudiante() {
        return this.nombreEstudiante;
    }
    
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
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
    public Set<HistorialGraduacion> getHistorialGraduacions() {
        return this.historialGraduacions;
    }
    
    public void setHistorialGraduacions(Set<HistorialGraduacion> historialGraduacions) {
        this.historialGraduacions = historialGraduacions;
    }




}


