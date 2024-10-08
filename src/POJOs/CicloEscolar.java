package POJOs;
// Generated 29-sep-2024 19:19:33 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CicloEscolar generated by hbm2java
 */
public class CicloEscolar  implements java.io.Serializable {


     private Integer idCiclo;
     private Escuela escuela;
     private int anio;
     private String estado;
     private Boolean borradoLogico;
     private Set grados = new HashSet(0);
     private Set historialGraduacions = new HashSet(0);
     private Set estudiantes = new HashSet(0);

    public CicloEscolar() {
    }

	
    public CicloEscolar(int anio) {
        this.anio = anio;
    }
    public CicloEscolar(Escuela escuela, int anio, String estado, Boolean borradoLogico, Set grados, Set historialGraduacions, Set estudiantes) {
       this.escuela = escuela;
       this.anio = anio;
       this.estado = estado;
       this.borradoLogico = borradoLogico;
       this.grados = grados;
       this.historialGraduacions = historialGraduacions;
       this.estudiantes = estudiantes;
    }
   
    public Integer getIdCiclo() {
        return this.idCiclo;
    }
    
    public void setIdCiclo(Integer idCiclo) {
        this.idCiclo = idCiclo;
    }
    public Escuela getEscuela() {
        return this.escuela;
    }
    
    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }
    public int getAnio() {
        return this.anio;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Boolean getBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(Boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }
    public Set getGrados() {
        return this.grados;
    }
    
    public void setGrados(Set grados) {
        this.grados = grados;
    }
    public Set getHistorialGraduacions() {
        return this.historialGraduacions;
    }
    
    public void setHistorialGraduacions(Set historialGraduacions) {
        this.historialGraduacions = historialGraduacions;
    }
    public Set getEstudiantes() {
        return this.estudiantes;
    }
    
    public void setEstudiantes(Set estudiantes) {
        this.estudiantes = estudiantes;
    }




}


