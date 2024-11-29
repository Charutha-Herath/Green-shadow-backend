package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.*;
import lk.ijse.greenshadow.DTO.FieldStatus;
import lk.ijse.greenshadow.DTO.IMPL.FieldDTO;
import lk.ijse.greenshadow.Entity.IMPL.*;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Service.FieldService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceIMPL implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CropDao cropDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Autowired
    private EquipmentDao equipmentDao;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        int number = 0;
        FieldEntity field = fieldDao.findLastRowNative();
        if (field != null) {
            String[] parts = field.getFieldCode().split("-");
            number = Integer.parseInt(parts[1]);
        }
        fieldDTO.setFieldCode("FIELD-" + ++number);
        FieldEntity fieldEntity = mapping.toFieldEntity(fieldDTO);

        List<StaffEntity> staffEntities = new ArrayList<>();
        for (String memberCode : fieldDTO.getStaffList()) {
            if (staffDao.existsById(memberCode)) {
                staffEntities.add(staffDao.getReferenceById(memberCode));
            }
        }
        List<CropEntity>cropEntities=new ArrayList<>();
        for (String cropCode :fieldDTO.getCropsList()){
            if (cropDao.existsById(cropCode)){
                cropEntities.add(cropDao.getReferenceById(cropCode));
            }
        }
        List<LogEntity>logEntities =new ArrayList<>();
        for (String logCode : fieldDTO.getLogsList()){
            if (monitoringLogDao.existsById(logCode)){
                logEntities.add(monitoringLogDao.getReferenceById(logCode));
            }
        }
        List<EquipmentEntity>equipmentEntities=new ArrayList<>();
        for (String eqid :fieldDTO.getEquipmentsList()){
            if (equipmentDao.existsById(eqid)){
                equipmentEntities.add(equipmentDao.getReferenceById(eqid));
            }
        }

        fieldEntity.setStaffList(staffEntities);
        fieldEntity.setCropList(cropEntities);
        fieldEntity.setLogList(logEntities);
        fieldEntity.setEquipmentsList(equipmentEntities);

        FieldEntity field1 = fieldDao.save(fieldEntity);
        if (field1 == null) {
            throw new DataPersistException("Something went wrong");
        }


    }

    @Override
    public List<FieldDTO> getAllFields() {
        return null;
    }

    @Override
    public FieldStatus getField(String fieldCode) {
        return null;
    }

    @Override
    public void deleteFields(String fieldCode) {

    }

    @Override
    public void updateField(String fieldCode, FieldDTO fieldDTO) {

    }


}
