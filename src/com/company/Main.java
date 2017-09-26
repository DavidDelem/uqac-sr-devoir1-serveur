package com.company;

/**
 * MAIN SERVEUR - Lance le serveur
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class Main {

    public static void main(String[] args) {

        /* Port désiré pour la socket (doit correspondre à celle du client) */
        int port = 19998;

        /* Dossier ou seront enregistrées et compilées (de façon temporaire) les classes reçues */
        String pathTmp = System.getProperty("java.io.tmpdir") + "tp1uqactmp";

        /* Lancement d'une socket côté serveur sur le port désiré */
        SocketServer socketServer = new SocketServer(port, pathTmp);
    }

}
