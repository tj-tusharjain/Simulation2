import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoading {

    Gson gson = new Gson();

    //Code snippet taken from courseGrades assignment.
    public static String getFileAsString(String filename) {

        final Path path = FileSystems.getDefault().getPath("data", filename);

        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Couldn't find file: " + filename);
            System.exit(-1);
            return null;
        }
    }

    public Simulation loadFile(){

        Simulation gameSimulation = gson.fromJson(getFileAsString("RestaurantSimulation.json"),
                Simulation.class);

        return gameSimulation;

    }

}
