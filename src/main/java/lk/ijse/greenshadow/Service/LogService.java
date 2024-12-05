package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.entity.Log;
import lk.ijse.greenshadow.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public List<Log> getAllLogs(String sortBy) {
        if(sortBy != null) {
            return logRepository.findAll(Sort.by(sortBy));
        }
        return logRepository.findAll();
    }

    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    public Log createLog(Log log) {
        return logRepository.save(log);
    }

    public Log updateLog(Long id, Log logDetails) {
        Log log = logRepository.findById(id).orElseThrow(() -> new RuntimeException("Log not found"));

        log.setDate(logDetails.getDate());
        log.setDetails(logDetails.getDetails());
        log.setObservedImage(logDetails.getObservedImage());

        return logRepository.save(log);
    }

    public void deleteLog(Long id) {
        Log log = logRepository.findById(id).orElseThrow(() -> new RuntimeException("Log not found"));

        logRepository.deleteById(id);
    }
}
