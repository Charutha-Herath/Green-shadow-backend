package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.EquipmentDao;
import lk.ijse.greenshadow.DAO.FieldDao;
import lk.ijse.greenshadow.DAO.StaffDao;
import lk.ijse.greenshadow.DTO.IMPL.EquipmentDTO;
import lk.ijse.greenshadow.Entity.IMPL.EquipmentEntity;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Exception.NotFoundException;
import lk.ijse.greenshadow.Service.EquipmentService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class EquipmenrServiceIMPL implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FieldDao fieldDao;


    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        int number =0;
        EquipmentEntity equipmentEntity =equipmentDao.findLastRowNative();
        if (equipmentEntity!=null){
            String [] psrts =equipmentEntity.getEquipmentCode().split("-");
            number =Integer.parseInt(psrts[1]);
        }
    equipmentDTO.setEquipmentCode("EQUIPMENT-"+ ++number);
        List<FieldEntity>fieldEntities =new ArrayList<>();
            for (String fieldCode :equipmentDTO.getFieldList()){
                    fieldEntities.add(fieldDao.getReferenceById(fieldCode));
            }
            EquipmentEntity equipmentEntity1 =mapping.toEquipmentEntity(equipmentDTO);
            equipmentEntity1.setFieldList(fieldEntities);
            for (FieldEntity field :fieldEntities){
                field.getEquipmentsList().add(equipmentEntity1);
            }
            EquipmentEntity eq =equipmentDao.save(equipmentEntity1);

            if (eq==null){
                throw new DataPersistException("Something went wrong");
            }
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {

       List<EquipmentDTO>equipmentDTOS=new ArrayList<>();
        for (EquipmentEntity equipmentEntity:equipmentDao.findAll()){
            List<String>field =new ArrayList<>();
            for (FieldEntity fieldEntity:equipmentEntity.getFieldList()){
                field.add(fieldEntity.getFieldCode());
            }
            EquipmentDTO equipmentDTO=mapping.asEquipmentDtoList(equipmentEntity);
            equipmentDTO.setFieldList(field);
            equipmentDTOS.add(equipmentDTO);
        }
        return equipmentDTOS;
    }

    @Override
    public EquipmentDTO getEquipment(String equipmentId) {
            EquipmentEntity equipmentEntity =equipmentDao.getReferenceById(equipmentId);
            return mapping.toEquipmentDTO(equipmentEntity);
    }

    @Override
    public void deleteEquipment(String equipmentId) {
            if (equipmentDao.existsById(equipmentId)){
                EquipmentEntity equipmentEntity=equipmentDao.getReferenceById(equipmentId);
                List<FieldEntity>fieldEntities=equipmentEntity.getFieldList();

                for (FieldEntity field:fieldEntities){
                    List<EquipmentEntity>equipmentEntities=field.getEquipmentsList();
                    equipmentEntities.remove(equipmentEntity);
                }
                equipmentEntity.getFieldList().clear();
                equipmentDao.delete(equipmentEntity);
            }else {
                throw new NotFoundException("Ypu entered Equipment ID not found");
            }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {
        Optional<EquipmentEntity> equipmentEntity = equipmentDao.findById(equipmentId);
        if (equipmentEntity.isPresent()) {
            equipmentEntity.get().setName(equipmentDTO.getName());
            equipmentEntity.get().setType(equipmentDTO.getType());
            equipmentEntity.get().setStatus(equipmentDTO.getStatus());
            equipmentEntity.get().setAvailableCount(equipmentDTO.getAvailableCount());

            List<FieldEntity> fieldEntities = new ArrayList<>();
            for (String field : equipmentDTO.getFieldList()) {
                fieldEntities.add(fieldDao.getReferenceById(field));
            }
            equipmentEntity.get().setFieldList(fieldEntities);
        }
    }
}
