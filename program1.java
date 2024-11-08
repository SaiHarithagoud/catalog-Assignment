import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PolynomialInterpolation {

    
    public static int decodeValue(int base, String value) {
        return Integer.parseInt(value, base);
    }

    
    public static double lagrangeInterpolation(List<int[]> points) {
        double c = 0;
        int k = points.size();

        for (int i = 0; i < k; i++) {
            int xi = points.get(i)[0];
            int yi = points.get(i)[1];

            double li = 1;
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    int xj = points.get(j)[0];
                    li *= (0 - xj) / (double) (xi - xj);
                }
            }
            c += yi * li;
        }
        return c;
    }

    
    public static void findConstantTerm(String filePath) {
        try {
            
            Gson gson = new Gson();
            FileReader reader = new FileReader(filePath);
            Map<String, Object> data = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            reader.close();

            Map<String, Double> keys = (Map<String, Double>) data.get("keys");
            int n = keys.get("n").intValue();
            int k = keys.get("k").intValue();

            
            List<int[]> points = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                Map<String, String> pointData = (Map<String, String>) data.get(String.valueOf(i));
                if (pointData != null) {
                    int x = i;
                    int base = Integer.parseInt(pointData.get("base"));
                    String value = pointData.get("value");
                    int y = decodeValue(base, value);
                    points.add(new int[]{x, y});
                }
            }

            
            List<int[]> selectedPoints = points.subList(0, k);

            
            double constantTerm = lagrangeInterpolation(selectedPoints);

            System.out.println("Constant term (c): " + constantTerm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
        findConstantTerm("input.json");
    }
}

        
    
            
            
