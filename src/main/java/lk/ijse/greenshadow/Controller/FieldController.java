package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import org.springframework.data.geo.Point;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.entity.Field;
import lk.ijse.greenshadow.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<List<Field>> getAllFields(@RequestParam(required = false) String sortBy) {
        List<Field> fields = fieldService.getAllFields(sortBy);
        return ResponseEntity.ok(fields);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<Field> getFieldById(@PathVariable Long id) {
        Field field = fieldService.getFieldById(id)
                .orElseThrow(() -> new RuntimeException("Field not found"));
        return ResponseEntity.ok(field);
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Field> createField(
            @RequestParam("name") String name,
            @RequestParam("landSize") double landSize,
            @RequestParam("location") Point location, // Example: JSON or "latitude,longitude"
            @RequestParam(value = "fieldImage1", required = false) MultipartFile fieldImage1,
            @RequestParam(value = "fieldImage2", required = false) MultipartFile fieldImage2) {

        String image1Data = null;
        String image2Data = null;

        try {
            if (fieldImage1 != null) {
                image1Data = new String(fieldImage1.getBytes());
            }
            if (fieldImage2 != null) {
                image2Data = new String(fieldImage2.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process images", e);
        }

        Field field = new Field();
        field.setName(name);
        field.setLandSize(landSize);
        field.setLocation(parseLocation(location));
        field.setFieldImage1(image1Data);
        field.setFieldImage2(image2Data);

        Field createdField = fieldService.createField(field);
        return ResponseEntity.ok(createdField);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Field> updateField(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("landSize") double landSize,
            @RequestParam("location") Point location,
            @RequestParam(value = "fieldImage1", required = false) MultipartFile fieldImage1,
            @RequestParam(value = "fieldImage2", required = false) MultipartFile fieldImage2) {

        String image1Data = null;
        String image2Data = null;

        try {
            if (fieldImage1 != null) {
                image1Data = new String(fieldImage1.getBytes());
            }
            if (fieldImage2 != null) {
                image2Data = new String(fieldImage2.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process images", e);
        }

        Field fieldDetails = new Field();
        fieldDetails.setName(name);
        fieldDetails.setLandSize(landSize);
        fieldDetails.setLocation(parseLocation(location));
        fieldDetails.setFieldImage1(image1Data);
        fieldDetails.setFieldImage2(image2Data);

        Field updatedField = fieldService.updateField(id, fieldDetails);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.ok(new MessageResponse("Field deleted successfully!"));
    }

    private Point parseLocation(Point location) {
        return location;
    }
}