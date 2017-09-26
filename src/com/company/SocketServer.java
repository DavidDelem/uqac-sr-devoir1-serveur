package com.company;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe SocketServer permet d'ouvrir une socket
 * pour se mettre à l'écoute des appels d'un client
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class SocketServer {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static int port;

    public SocketServer(int portSelected, String pathTmp) {

        try {
            /* Initialisation de la socket serveur */
            port = portSelected;
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur initialisé");

            while (true) {

                /* Récupération des données transmises par le client */
                socket = serverSocket.accept();
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                Datas receivedDatas = (Datas) inStream.readObject();

                /* Traitement des informations sur la méthode à executer et ses paramètres */
                String[] datas = receivedDatas.getInformationsExecution().split("&");
                String classe = datas[0];
                String methode = datas[1];
                String[] params = datas[2].split(",");

                /* Traitement du fichier ou de l'objet reçu */
                Object object = new Object();
                MethodExecutor methodExecutor = null;
                Object result = null;

                switch (receivedDatas.getProtocole()) {
                    case "SOURCEColl":

                        /* CAS 1 : un fichier .java à été reçu */
                        /* Enregistrement puis compilation de la classe reçue */

                        String pathFile = pathTmp + "\\" + classe + ".java";
                        JavaFileManager.createDirectory(pathTmp);
                        JavaFileManager.saveFile(receivedDatas.getFile(), pathFile);
                        JavaFileManager.removePackageName(pathFile);
                        JavaFileManager.compileFile(pathFile, pathTmp);
                        methodExecutor = new MethodExecutor(classe, pathTmp);
                        break;

                    case "BYTEColl":

                        /* CAS 2 : un fichier .class déjà compilé à été reçu */
                        /* Enregistrement du fichier reçu */

                        JavaFileManager.createDirectory(pathTmp);
                        JavaFileManager.saveFile(receivedDatas.getFile(), pathTmp + "\\" + classe + ".class");
                        methodExecutor = new MethodExecutor(classe, pathTmp);
                        break;

                    case "OBJECTColl":

                        /* CAS 3 : un objet de la classe à été reçu */
                        /* Enregistrement du fichier reçu */

                        object = receivedDatas.getObject();
                        methodExecutor = new MethodExecutor(classe, object);
                        break;
                    default:
                        break;
                }

                /* Execution de la méthode demandée et récupération de son retour */
                result = methodExecutor.executeMethod(methode, params);

                /* Suppression des répertoires et des fichiers crées */
                JavaFileManager.deleteDirectoryAndContent(pathTmp);

                /* Envoi de la réponse au client */
                String serverResponse = "" + result + (char) 13;
                BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "US-ASCII");
                outputStreamWriter.write(serverResponse);
                outputStreamWriter.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
