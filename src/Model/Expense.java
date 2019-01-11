package Model;
import java.io.Serializable;

/**
 * Clase que define objetos de tipo Expense(Gasto)
 * @author: Fiorella Rodriguez
 */

public class Expense implements Serializable {
    private Integer _id;
    private String _concept;
    private double _amount;
    private Integer _classification;
    private String _date;

    /**
     * Constructor para Expense
     * @param _id id
     * @param _concept concepto del gasto
     * @param _amount monto del gasto
     * @param _classification id de la clasificacion a la que pertenece
     * @param _date fecha en la que se realiza el gasto
     */
    public Expense(Integer _id, String _concept, double _amount, Integer _classification, String _date) {
        this._id = _id;
        this._concept = _concept;
        this._amount = _amount;
        this._classification = _classification;
        this._date = _date;
    }

    public Expense(){

    }

    public long get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_concept() {
        return _concept;
    }

    public void set_concept(String _concept) {
        this._concept = _concept;
    }

    public double get_amount() {
        return _amount;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public Integer get_classification() {
        return _classification;
    }

    public void set_classification(Integer _classification) {
        this._classification = _classification;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    @Override
    public String toString() {
        return this._concept + "               " + this._amount + "             " + this._date ;
    }
}
