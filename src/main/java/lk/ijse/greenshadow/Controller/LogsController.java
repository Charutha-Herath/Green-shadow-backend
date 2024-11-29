package lk.ijse.greenshadow.Controller;


import lk.ijse.greenshadow.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Service.MonitoringLogService;
import lk.ijse.greenshadow.Util.PicEncorder;
import lk.ijse.greenshadow.Util.SplitString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v2/logs")
public class LogsController {
    @Autowired
    private MonitoringLogService monitoringLogService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog(@RequestPart("logDate") String date,
                                        @RequestPart("logDetails") String details,
                                        @RequestPart("logImg") MultipartFile img,
                                       @RequestPart("field") String field,
                                       @RequestPart("crop")String crop,
                                       @RequestPart("staff")String staff)
    {
        try {
            List<String> field_codes =new ArrayList<>();

            if (field!= null) {
                field_codes = SplitString.spiltLists(field);
            }

            List<String> crop_codes =new ArrayList<>();

            if (crop !=null){
                crop_codes=SplitString.spiltLists(crop);
            }

            List<String>straff_codes =new ArrayList<>();

            if (staff !=null){
                straff_codes =SplitString.spiltLists(staff);
            }

            String image = PicEncorder.generatePicture(img);
            MonitoringLogDTO logDto = new MonitoringLogDTO();
                logDto.setLogDate(date);
                logDto.setLogDetails(details);
                logDto.setObservedImage(image);
                logDto.setFieldList(field_codes);
                logDto.setCropList(crop_codes);
                logDto.setStaffList(straff_codes);

            System.out.println("log dto : "+logDto);

            monitoringLogService.saveLog(logDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<MonitoringLogDTO>getAll(){
        return monitoringLogService.getAllLogs();
    }
    @DeleteMapping(value = "/{logCode}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>deleteLog(@PathVariable("logCode")String log){
        try {
            monitoringLogService.deleteLog(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void>updateLog(@PathVariable("logCode")String logCode,
                                         @RequestPart("logDate") String date,
                                         @RequestPart("logDetails") String details,
                                         @RequestPart("logImg") MultipartFile img,
                                         @RequestPart("field") String field,
                                         @RequestPart("crop")String crop,
                                         @RequestPart("staff")String staff) {
        try {
            String logImg = PicEncorder.generatePicture(img);
            List<String> fieldCodes = new ArrayList<>();
            List<String> cropCodes = new ArrayList<>();
            List<String> stafList = new ArrayList<>();

            if (field != null) {
                fieldCodes = SplitString.spiltLists(field);
            }
            if (crop != null) {
                cropCodes = SplitString.spiltLists(crop);
            }
            if (staff != null) {
                stafList = SplitString.spiltLists(staff);
            }

            MonitoringLogDTO mldto = new MonitoringLogDTO();
            mldto.setLogCode(logCode);
            mldto.setLogDate(date);
            mldto.setLogDetails(details);
            mldto.setObservedImage(logImg);
            mldto.setFieldList(fieldCodes);
            mldto.setCropList(cropCodes);
            mldto.setStaffList(stafList);

            monitoringLogService.updateLog(logCode, mldto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

