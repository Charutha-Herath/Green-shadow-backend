package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.entity.Crop;
import lk.ijse.greenshadow.entity.Field;
import lk.ijse.greenshadow.repository.CropRepository;
import lk.ijse.greenshadow.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FieldRepository fieldRepository; // Add FieldRepository to handle Field entities

    public List<Crop> getAllCrops(String sortBy) {
        if (sortBy != null) {
            return cropRepository.findAll(Sort.by(sortBy));
        }
        return cropRepository.findAll();
    }

    public Optional<Crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }

    public Crop createCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public Crop updateCrop(Long id, Crop cropDetails) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        crop.setCropName(cropDetails.getCropName());
        crop.setScientificName(cropDetails.getScientificName());
        crop.setCropImage(cropDetails.getCropImage());
        crop.setCategory(cropDetails.getCategory());
        crop.setSeason(cropDetails.getSeason());
        crop.setField(cropDetails.getField());

        return cropRepository.save(crop);
    }

    public void deleteCrop(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        cropRepository.delete(crop);
    }

    // New method to fetch a Field by its ID
    public Field getFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Field not found"));
    }
}