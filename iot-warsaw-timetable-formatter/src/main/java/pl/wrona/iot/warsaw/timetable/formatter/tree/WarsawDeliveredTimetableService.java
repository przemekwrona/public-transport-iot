package pl.wrona.iot.warsaw.timetable.formatter.tree;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
@AllArgsConstructor
public class WarsawDeliveredTimetableService {

    public WarsawTree load(String path) throws IOException {
        return load(new File(path));
    }

    public WarsawTree load(File file) throws IOException {
        WarsawTree warsawTree = new WarsawTree();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                warsawTree.add(line);
            }
        }

        return warsawTree;
    }
}
