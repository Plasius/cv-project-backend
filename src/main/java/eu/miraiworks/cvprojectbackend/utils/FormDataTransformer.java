package eu.miraiworks.cvprojectbackend.utils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FormDataTransformer {

    public Map<String, Object> transformFormData(Map<String, String> formData) {
        List<String> columns = List.of(
                "City", "State", "Bank", "BankState", "NAICS", "Term", "NoEmp",
                "UrbanRural", "RevLineCr", "GrAppv", "RealEstate", "Recession", "Default"
        );

        List<Integer> index = List.of(0); // Assuming you want to transform the first two rows

        List<Object> dataRow = new ArrayList<>();
        dataRow.add(formData.getOrDefault("City", ""));
        dataRow.add(formData.getOrDefault("State", ""));
        dataRow.add(formData.getOrDefault("Bank", ""));
        dataRow.add(formData.getOrDefault("BankState", ""));
        dataRow.add(Integer.parseInt(formData.getOrDefault("NAICS", "0")));
        dataRow.add(Integer.parseInt(formData.getOrDefault("Term", "0")));
        dataRow.add(Integer.parseInt(formData.getOrDefault("NoEmp", "0")));
        dataRow.add(Integer.parseInt(formData.getOrDefault("UrbanRural", "0")));
        dataRow.add(formData.getOrDefault("RevLineCr", ""));
        dataRow.add(formatCurrency(formData.getOrDefault("GrAppv", "0")));
        dataRow.add(Boolean.parseBoolean(formData.getOrDefault("RealEstate", "false")));
        dataRow.add(Boolean.parseBoolean(formData.getOrDefault("Recession", "false")));
        dataRow.add(Integer.parseInt(formData.getOrDefault("Default", "0")));

        List<List<Object>> data = new ArrayList<>();
        data.add(dataRow);

        return Map.of(
                "input_data", Map.of(
                        "columns", columns,
                        "index", index,
                        "data", data
                )
        );
    }

    private String formatCurrency(String amountStr) {
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            return String.format("$%,.2f", amount);
        } catch (NumberFormatException e) {
            return "$0.00";
        }
    }
}
