import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

public class Converter {
    public static String convertBlobToString(Blob blob) throws SQLException, IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = blob.getBinaryStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    public static Blob convertStringToBlob(String string) throws SQLException {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        Blob blob = new SerialBlob(bytes);
        return blob;
    }
}
