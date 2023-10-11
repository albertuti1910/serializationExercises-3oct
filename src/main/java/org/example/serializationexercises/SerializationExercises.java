package org.example.serializationexercises;

import com.google.gson.Gson;
import org.example.serializationexercises.model.Movie;
import org.example.serializationexercises.model.Session;
import org.example.serializationexercises.model.Theater;

import java.io.*;

public class SerializationExercises {
    /*
        Should define the class for the concepts Movie, Theater and Session.
        A session is a play of movie in a theater.
        Create 2 instances of each class and relate among them.
        Serialize to Json all objects and save then in different files.
     */

    public static class Exercise1 {
        public static void main(String[] args) {
            Movie movie1 = new Movie("Oppenheimer", 180);
            Movie movie2 = new Movie("Barbie", 114);
            Theater theater1 = new Theater("El Muelle", "Puerto");
            Theater theater2 = new Theater("Las Arenas", "Auditorio");
            Session session1 = new Session(movie1, theater1, "2023-10-03 18:00");
            Session session2 = new Session(movie2, theater2, "2023-10-03 20:30");

            Gson gson = new Gson();

            saveToFile("movie1.json", gson.toJson(movie1));
            saveToFile("movie2.json", gson.toJson(movie2));
            saveToFile("theater1.json", gson.toJson(theater1));
            saveToFile("theater2.json", gson.toJson(theater2));
            saveToFile("session1.json", gson.toJson(session1));
            saveToFile("session2.json", gson.toJson(session2));
        }

        private static void saveToFile(String fileName, String content) {
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(content);
                System.out.println("Saved to " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        Deserialize the objects created in exercise 1.
        Now serialize them using ObjectOutputStream
     */
    public static class Exercise2 {

        public static void main(String[] args) {
            Gson gson = new Gson();

            Movie movie1 = gson.fromJson(readFromFile("movie1.json"), Movie.class);
            Movie movie2 = gson.fromJson(readFromFile("movie2.json"), Movie.class);
            Theater theater1 = gson.fromJson(readFromFile("theater1.json"), Theater.class);
            Theater theater2 = gson.fromJson(readFromFile("theater2.json"), Theater.class);
            Session session1 = gson.fromJson(readFromFile("session1.json"), Session.class);
            Session session2 = gson.fromJson(readFromFile("session2.json"), Session.class);

            serializeObject("movie1.ser", movie1);
            serializeObject("movie2.ser", movie2);
            serializeObject("theater1.ser", theater1);
            serializeObject("theater2.ser", theater2);
            serializeObject("session1.ser", session1);
            serializeObject("session2.ser", session2);
        }

        private static String readFromFile(String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private static void serializeObject(String fileName, Object object) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeObject(object);
                System.out.println("Serialized " + object.getClass().getSimpleName() + " to " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
       Deserialize the objects from the binary files created in exercise 2.
    */
    public static class Exercise3 {

        public static void main(String[] args) {
            printDeserializedInfo("movie1.ser", Movie.class);
            printDeserializedInfo("movie2.ser", Movie.class);
            printDeserializedInfo("theater1.ser", Theater.class);
            printDeserializedInfo("theater2.ser", Theater.class);
            printDeserializedInfo("session1.ser", Session.class);
            printDeserializedInfo("session2.ser", Session.class);
        }

        private static <T> void printDeserializedInfo(String fileName, Class<T> clazz) {
            T deserializedObject = deserializeObject(fileName, clazz);
            if (deserializedObject != null) {
                System.out.println("Deserialization successful for " + clazz.getSimpleName() + ": " + getInfo(deserializedObject));
            } else {
                System.out.println("Deserialization failed for " + clazz.getSimpleName());
            }
        }

        private static <T> String getInfo(T object) {
            if (object instanceof Movie) {
                Movie movie = (Movie) object;
                return "Name: " + movie.getTitle() + ", Duration: " + movie.getDuration();
            } else if (object instanceof Theater) {
                Theater theater = (Theater) object;
                return "Name: " + theater.getName();
            } else if (object instanceof Session) {
                Session session = (Session) object;
                return "Name: " + session.getMovie() + ", Duration: " + session.getTime();
            } else {
                return "Unknown object type";
            }
        }

        private static <T> T deserializeObject(String fileName, Class<T> clazz) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                Object object = ois.readObject();
                if (clazz.isInstance(object)) {
                    return clazz.cast(object);
                } else {
                    throw new ClassCastException("Unexpected class type during deserialization");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}