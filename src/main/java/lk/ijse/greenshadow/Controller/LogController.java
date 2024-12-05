package lk.ijse.greenshadow.controller;

import lk.ijse.greenshadow.entity.Log;
import lk.ijse.greenshadow.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<List<Log>> getAllLogs(@RequestParam(required = false) String sortBy) {
        List<Log> logs = logService.getAllLogs(sortBy);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<Log> getLogById(@PathVariable Long id) {
        Log log = logService.getLogById(id).orElseThrow(() -> new RuntimeException("Log not found"));
        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Log> updateLog(@PathVariable Long id, @RequestBody Log log) {
        Log updatedLog = logService.updateLog(id, log);
        return ResponseEntity.ok(updatedLog);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        Log createdLog = logService.createLog(log);
        return ResponseEntity.ok(createdLog);
    }
}
