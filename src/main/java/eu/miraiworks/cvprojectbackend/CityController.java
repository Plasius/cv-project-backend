package eu.miraiworks.cvprojectbackend;

import eu.miraiworks.cvprojectbackend.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public List<String> getCities(@RequestParam String q) {
        String query = q.toLowerCase();
        return cityService.getCitiesByNameStartingWith(query);
    }
}
