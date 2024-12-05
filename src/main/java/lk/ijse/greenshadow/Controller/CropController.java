package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.entity.Crop;
import lk.ijse.greenshadow.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/crops")
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

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Crop> createCrop(@Valid @RequestBody Crop crop) {
        Crop createdCrop = cropService.createCrop(crop);
        return ResponseEntity.ok(createdCrop);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Crop> updateCrop(@PathVariable Long id, @Valid @RequestBody Crop cropDetails) {
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
