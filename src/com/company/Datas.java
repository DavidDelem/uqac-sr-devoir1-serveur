package com.company;

import java.io.Serializable;

/**
 * La classe Datas défini une structure de données commune pour transférer
 * les informations entre un client et le serveur
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class Datas implements Serializable {

    private String informationsExecution; // De la forme Classe&methode&arg1,arg2,...
    private String protocole; // SOURCEColl, OBJECTColl ou  OBJECTColl
    private byte[] file; // Fichier .java ou .class pour les protocoles SOURCEColl, OBJECTColl
    private Object object; // Objet sérialisé pour le protocole OBJECTColl uniquement

    public Datas() {
    }

    public String getInformationsExecution() {
        return informationsExecution;
    }

    public void setInformationsExecution(String informationsExecution) {
        this.informationsExecution = informationsExecution;
    }

    public String getProtocole() {
        return protocole;
    }

    public void setProtocole(String protocole) {
        this.protocole = protocole;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
