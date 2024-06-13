package eu.miraiworks.cvprojectbackend;
import eu.miraiworks.cvprojectbackend.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // Method to get all cities
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Method to save a city
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    // Method to find cities by name starting with 'query'
    public List<String> getCitiesByNameStartingWith(String query) {
        List<String> cityNames = new ArrayList<>();
        List<City> cities = cityRepository.findTop5ByNameStartingWith(query.toLowerCase());

        for (City city : cities) {
            cityNames.add(city.getName());
        }

        return cityNames;
    }
}
