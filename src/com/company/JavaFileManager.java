package com.company;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * La classe SocketServer permet de manipuler et de compiler
 * des fichiers java
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class JavaFileManager {

    /**
     * Enregistre un fichier sur le disque
     *
     * @param  file     le fichier représenté sous forme de byte[]
     * @param  pathFile le chemin d'enregistrement du fichier
     * @return
     */

    public static void saveFile(byte[] file, String pathFile) {
        try {
            FileOutputStream outputStream = new FileOutputStream(pathFile);
            outputStream.write(file);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compile un fichier java lors de l'execution du programme
     *
     * @param  pathFile     le chemin du fichier .java à compiler
     * @param pathDirectory le répertoire ou seront placées les fichiers .class générés
     * @return
     */

    public static void compileFile(String pathFile, String pathDirectory) {
        try {
            /* Récupération du fichier */
            File sourceFile = new File(pathFile);

            /* Compilation du fichier */
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(pathDirectory)));
            compiler.getTask(null, fileManager,null,null,null, fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un nouveau répertoire
     *
     * @param  pathDirectory    le chemin du répertoire à créer
     * @return
     */

    public static void createDirectory(String pathDirectory) {
        try {
            Files.createDirectories(Paths.get(pathDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un répertoire et son contenu de façon récursive
     *
     * @param  pathDirectory    le chemin du répertoire à supprimer
     * @return
     */

    public static void deleteDirectoryAndContent(String pathDirectory) {
        File dir = new File(pathDirectory);

        if (dir.isDirectory()) {
            for (File sub : dir.listFiles()) {
                deleteDirectoryAndContent(sub.getAbsolutePath());
            }
        }
        dir.delete();
    }

    /**
     * Analyse le contenu d'un fichier pour retirer, si elle est
     * présente, la ligne indiquant le package de la classe
     *
     * @param  pathFile le chemin du fichier
     * @return
     */

    public static void removePackageName(String pathFile) {

        try {
            /* Ouverture du fichier */
            File file = new File(pathFile);
            Scanner scanner = new Scanner(file);

            /* Analyse des lignes du fichier */
            ArrayList<String> lignes = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.startsWith("package")) lignes.add(line);
            }
            scanner.close();

            /* Enregistrement du nouveau fichier */
            FileWriter writer = new FileWriter(file);
            for (String line : lignes) writer.write(line);
            writer.close();
        } catch (IOException e) {

        }
    }

}
