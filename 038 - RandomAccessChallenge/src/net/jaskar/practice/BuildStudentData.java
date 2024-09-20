package net.jaskar.practice;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildStudentData {
    public static void build(String datFileName, boolean separateIndexFile) {
        Path resources = Path.of("resources");
        Path studentJson = resources.resolve("students.json");
        Path dataFile = resources.resolve(datFileName + ".dat");
        Map<Long, Long> indexedIds = new LinkedHashMap<>();

        try {
            Files.deleteIfExists(dataFile);
            String data = Files.readString(studentJson);
            data = data.replaceFirst("^(\\[)", "")
                    .replaceFirst("(\\])$", "");
            var records = data.split(System.lineSeparator());
            System.out.println("# of records = " + records.length);

            long startingPos = separateIndexFile ? 0 : 4 + (16L * records.length);

            Pattern idPattern = Pattern.compile("studentId\":([0-9]+)");
            try (RandomAccessFile ra = new RandomAccessFile(dataFile.toString(), "rw")) {
                ra.seek(startingPos);
                for (String record : records) {
                    Matcher m = idPattern.matcher(record);
                    if (m.find()) {
                        long id = Long.parseLong(m.group(1));
                        indexedIds.put(id, ra.getFilePointer());
                        ra.writeUTF(record);
                    }
                }
                String indexFile = resources.resolve(datFileName + ".idx").toString();
                writeIndex((separateIndexFile) ? new RandomAccessFile(indexFile, "rw") : ra, indexedIds);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeIndex(RandomAccessFile ra, Map<Long, Long> indexMap) {
        try {
            ra.seek(0);
            ra.writeInt(indexMap.size());
            for (var studentIdx : indexMap.entrySet()) {
                ra.writeLong(studentIdx.getKey());
                ra.writeLong(studentIdx.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
