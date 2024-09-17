package POJOs;
// Generated 16-sep-2024 21:46:47 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Estudiante generated by hbm2java
 */
public class Estudiante  implements java.io.Serializable {


     private int idEstudiante;
     private String codigoPersonal;
     private String cui;
     private String apellidos;
     private String nombres;
     private Date fechaNacimiento;
     private String genero;
     private Date fechaCreacion;
     private Boolean borradoLogico;
     private Set<Inscripcion> inscripcions = new HashSet<Inscripcion>(0);

    public Estudiante() {
    }

	
    public Estudiante(int idEstudiante, String codigoPersonal, String cui, String apellidos, String nombres, Date fechaNacimiento, String genero) {
        this.idEstudiante = idEstudiante;
        this.codigoPersonal = codigoPersonal;
        this.cui = cui;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }
    public Estudiante(int idEstudiante, String codigoPersonal, String cui, String apellidos, String nombres, Date fechaNacimiento, String genero, Date fechaCreacion, Boolean borradoLogico, Set<Inscripcion> inscripcions) {
       this.idEstudiante = idEstudiante;
       this.codigoPersonal = codigoPersonal;
       this.cui = cui;
       this.apellidos = apellidos;
       this.nombres = nombres;
       this.fechaNacimiento = fechaNacimiento;
       this.genero = genero;
       this.fechaCreacion = fechaCreacion;
       this.borradoLogico = borradoLogico;
       this.inscripcions = inscripcions;
    }
   
    public int getIdEstudiante() {
        return this.idEstudiante;
    }
    
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    public String getCodigoPersonal() {
        return this.codigoPersonal;
    }
    
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public String getCui() {
        return this.cui;
    }
    
    public void setCui(String cui) {
        this.cui = cui;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getGenero() {
        return this.genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Boolean getBorradoLogico() {
        return this.borradoLogico;
    }
    
    public void setBorradoLogico(Boolean borradoLogico) {
        this.borradoLogico = borradoLogico;
    }
    public Set<Inscripcion> getInscripcions() {
        return this.inscripcions;
    }
    
    public void setInscripcions(Set<Inscripcion> inscripcions) {
        this.inscripcions = inscripcions;
    }




}


