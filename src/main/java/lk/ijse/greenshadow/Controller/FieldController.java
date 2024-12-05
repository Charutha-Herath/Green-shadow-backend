package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.entity.Field;
import lk.ijse.greenshadow.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/fields")
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

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Field> createField(@Valid @RequestBody Field field) {
        Field createdField = fieldService.createField(field);
        return ResponseEntity.ok(createdField);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Field> updateField(@PathVariable Long id, @Valid @RequestBody Field fieldDetails) {
        Field updatedField = fieldService.updateField(id, fieldDetails);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.ok(new MessageResponse("Field deleted successfully!"));
    }
}
