package eu.miraiworks.cvprojectbackend;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.miraiworks.cvprojectbackend.utils.FormDataTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FormController {
    //TODO define endpoint in config
    @Value("${azureml.endpoint}")
    private String azureMlEndpoint;
    //TODO define APIKEY in config
    @Value("${azureml.apikey}")
    private String azureMlApiKey;

    //inject form transformer
    private final FormDataTransformer formDataTransformer;
    public FormController(FormDataTransformer formDataTransformer) {
        this.formDataTransformer = formDataTransformer;
    }

    @PostMapping("/form")
    public ResponseEntity<String> handleFormSubmission(@RequestBody Map<String, String> formData) {
        //transform received json(map) to new map format
        Map<String, Object> transformedData = formDataTransformer.transformFormData(formData);

        System.out.println(formData);
        System.out.println(transformedData);

        //create azure request template
        Map<String, Object> requestData = Map.of(
                "input_data", transformedData,
                "params", Map.of()
        );

        // Convert requestData to JSON string
        String jsonData;
        try {
            jsonData = new ObjectMapper().writeValueAsString(requestData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing form data.");
        }

        // Set up headers for the HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(azureMlApiKey);
        headers.set("azureml-model-deployment", "cv-project-deployment");

        // Create HTTP entity with body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);

        // Make the HTTP POST request to Azure ML endpoint
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    azureMlEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            if (response.getBody() != null) {
                if (response.getBody().contains("true")) {
                    return ResponseEntity.ok("{\"high_default_risk\": \"1\"}");
                } else {
                    return ResponseEntity.ok("{\"high_default_risk\": \"0\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // or .status(204).build()
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling Azure ML model endpoint.");
        }
    }
}