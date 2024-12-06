package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.entity.Crop;
import lk.ijse.greenshadow.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<List<Crop>> getAllCrops(@RequestParam(required = false) String sortBy) {
        List<Crop> crops = cropService.getAllCrops(sortBy);
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        Crop crop = cropService.getCropById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        return ResponseEntity.ok(crop);
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Crop> createCrop(
            @RequestParam("cropName") String cropName,
            @RequestParam(value = "scientificName", required = false) String scientificName,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "season", required = false) String season,
            @RequestParam(value = "fieldId") Long fieldId,
            @RequestParam(value = "cropImage", required = false) MultipartFile cropImage) {

        String cropImageData = null;

        try {
            if (cropImage != null) {
                cropImageData = new String(cropImage.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process crop image", e);
        }

        Crop crop = new Crop();
        crop.setCropName(cropName);
        crop.setScientificName(scientificName);
        crop.setCategory(category);
        crop.setSeason(season);
        crop.setCropImage(cropImageData);
        crop.setField(cropService.getFieldById(fieldId));

        Crop createdCrop = cropService.createCrop(crop);
        return ResponseEntity.ok(createdCrop);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Crop> updateCrop(
            @PathVariable Long id,
            @RequestParam("cropName") String cropName,
            @RequestParam(value = "scientificName", required = false) String scientificName,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "season", required = false) String season,
            @RequestParam(value = "fieldId") Long fieldId,
            @RequestParam(value = "cropImage", required = false) MultipartFile cropImage) {

        String cropImageData = null;

        try {
            if (cropImage != null) {
                cropImageData = new String(cropImage.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process crop image", e);
        }

        Crop cropDetails = new Crop();
        cropDetails.setCropName(cropName);
        cropDetails.setScientificName(scientificName);
        cropDetails.setCategory(category);
        cropDetails.setSeason(season);
        cropDetails.setCropImage(cropImageData);
        cropDetails.setField(cropService.getFieldById(fieldId));

        Crop updatedCrop = cropService.updateCrop(id, cropDetails);
        return ResponseEntity.ok(updatedCrop);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<?> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.ok(new MessageResponse("Crop deleted successfully!"));
    }
}