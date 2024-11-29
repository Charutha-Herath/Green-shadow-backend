package lk.ijse.greenshadow.Controller;

import lk.ijse.greenshadow.DTO.IMPL.FieldDTO;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Service.FieldService;
import lk.ijse.greenshadow.Util.IdGenerater;
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
@RequestMapping("api/v2/fields")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(@RequestPart("fieldName") String fieldName,
                                          @RequestPart("fieldLocation") String fieldLocation,
                                          @RequestPart("fieldSize") String fieldSize,
                                          @RequestPart("fieldImg1") MultipartFile fieldImg1,
                                          @RequestPart("fieldImg2") MultipartFile fieldImg2,
                                          @RequestPart("cropId")String cropList,
                                          @RequestPart("staffId")String staffList ){
        try {

            String img1 = PicEncorder.generatePicture(fieldImg1);
            String img2 = PicEncorder.generatePicture(fieldImg2);
            List<String>cropEntities=new ArrayList<>();
            List<String>staffEntities=new ArrayList<>();


            if (cropList!= null) {
                cropEntities = SplitString.spiltLists(cropList);
            }
            if (staffList!=null){
                staffEntities=SplitString.spiltLists(staffList);
            }


            FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setFieldCode(IdGenerater.generateId("FIELD-"));
                fieldDTO.setName(fieldName);
                fieldDTO.setLocation(fieldLocation);
                fieldDTO.setExtentSize(Double.parseDouble(fieldSize));
                fieldDTO.setFieldImage1(img1);
                fieldDTO.setFieldImage2(img2);
                fieldDTO.setCropsList(cropEntities);
                fieldDTO.setStaffList(staffEntities);

//            System.out.println(fieldDTO);

            fieldService.saveField(fieldDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

