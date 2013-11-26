package br.com.tclf.afd.model;

/**
 * Created with IntelliJ IDEA.
 * User: Renato
 * Date: 25/11/13
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
public class TableStates {
    private String key;
    private String value;

    public TableStates(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
