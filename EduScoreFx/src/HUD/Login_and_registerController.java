/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HUD;

import POJOs.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author hygro
 */
public class Login_and_registerController implements Initializable {

    @FXML
    private Button iniciar;
    @FXML
    private Label title;
    @FXML
    private TextField usuario;
    @FXML
    private Label textoinicio;
    @FXML
    private Label user;
    @FXML
    private Label pass;
    @FXML
    private TextField contrasena;
    @FXML
    private Button inser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    public void Insertar() {
        try {
            String usua, contra;
            usua = usuario.getText();
            contra = contrasena.getText();
            if (CRUDs.CUsuario.crear(usua, contra)) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion xd");
                alerta.setHeaderText(null);
                alerta.setContentText("La grasa lo respalda ;v");
                alerta.showAndWait();
                limpiar();
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
                alerta.setHeaderText(null);
                alerta.setContentText("No eres un papulince :'v");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion IMPORTANTE MAMAHUEVO");
            alerta.setHeaderText(null);
            alerta.setContentText("Error en: " + e);
            alerta.showAndWait();

        }
    }
    
    public static boolean validarUsuario(String username, String password) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        boolean esValido = false;

        try {
            session.beginTransaction();
            // Usamos Criteria para buscar el usuario con el username y contraseña proporcionados
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nombre_usuario", username));
            criteria.add(Restrictions.eq("contrasena", password));
            criteria.add(Restrictions.eq("borradorLogico", true)); // Consideramos solo los no borrados

            // Si encontramos al menos un resultado, es que las credenciales son válidas
            Usuario usuario = (Usuario) criteria.uniqueResult();
            if (usuario != null) {
                esValido = true;
            }
        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e);
        } finally {
            session.getTransaction().commit();
        }

        return esValido;
    }
    
    
    
    
    
    //Metodos extra
    public void limpiar(){
        usuario.setText("");
        contrasena.setText("");
    }
}
