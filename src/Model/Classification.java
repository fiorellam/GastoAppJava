package Model;
import java.io.Serializable;


/**
 * Clase que define objetos de tipo Classification(Gasto)
 * @author: Fiorella Rodriguez
 */
public class Classification implements Serializable {


    private Integer classification_id;
    private String namee;

    public double getLimitt() {
        return limitt;
    }

    public void setLimitt(double limitt) {
        this.limitt = limitt;
    }

    public double getActual_limit() {
        return actual_limit;
    }

    public void setActual_limit(double actual_limit) {
        this.actual_limit = actual_limit;
    }

    private double limitt;
    private double actual_limit;

    /**
     * Constructor para Classification
     * @param classification_id id
     * @param namee nombre de la clasificacion
     * @param limitt limite por clasificacion
     * @param actual_limit limite que se va actualizando segun los gastos que se vayan ingresando
     */
    public Classification(Integer classification_id, String namee, Integer limitt, Integer actual_limit) {
        this.classification_id = classification_id;
        this.namee = namee;
        this.limitt = limitt;
        this.actual_limit = actual_limit;
    }

    public Classification(){

    }

    public Integer getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(Integer classification_id) {
        this.classification_id = classification_id;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

//    public Integer getLimitt() {
//        return limitt;
//    }

    public void setLimitt(Integer limitt) {
        this.limitt = limitt;
    }
//    public Integer getActual_limit() {
//        return actual_limit;
//    }

    public void setActual_limit(Integer actual_limit) {
        this.actual_limit = actual_limit;
    }

    @Override
    public String toString() {
        return this.namee ;
    }
}
