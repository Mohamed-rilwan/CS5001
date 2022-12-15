package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.University;
import model.Constants.PropertyToCheck;

public class FileActionHandler {

    public boolean saveToFile(String path) {
        boolean isSaved = false;
        try (FileOutputStream fos = new FileOutputStream(new File(path));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Write objects to file
            oos.writeObject(null);
            isSaved = true;
            fos.close();
            oos.close();
            System.out.println(" Saved data succesfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        return isSaved;
    }

    public University readFromFile(String path, PropertyToCheck property) {
        University valueFromFile = null;
        try (
                FileInputStream fi = new FileInputStream(new File(path));
                ObjectInputStream oi = new ObjectInputStream(fi);) {
            valueFromFile = (University) oi.readObject();
            oi.close();
            fi.close();
            System.out.println("Data loaded succesfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getLocalizedMessage());
        }
        return valueFromFile;
    }
}
