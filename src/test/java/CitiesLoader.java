import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

public class CitiesLoader {
    private static final List<String> CITIES;

    static {
        try {
            InputStream inputStream = CitiesLoader.class.getClassLoader().getResourceAsStream("cities.json");
            if (inputStream == null) {
                throw new RuntimeException("Файл cities.json не найден в classpath");
            }
            ObjectMapper mapper = new ObjectMapper();
            CITIES = mapper.readValue(
                    inputStream,
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cities list", e);
        }
    }

    public static String getRandomCity() {
        return CITIES.get((int) (Math.random() * CITIES.size()));
    }
}
