package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.CropDao;
import lk.ijse.greenshadow.DAO.FieldDao;
import lk.ijse.greenshadow.DTO.IMPL.CropDTO;
import lk.ijse.greenshadow.Entity.IMPL.CropEntity;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Service.CropService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CropServiceIMPL implements CropService {

    @Autowired
    private CropDao cropDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldDao fieldDao;


    @Override
    public void saveCrop(CropDTO cropDTO) {
        int number =0;
        CropEntity crop =cropDao.findLastRowNative();
        if (crop != null) {
            String[] parts = crop.getCropCode().split("-");
            number = Integer.parseInt(parts[1]);
        }
            cropDTO.setCropCode("CROP-"+ ++number);
            CropEntity cropEntity =mapping.toCropEntity(cropDTO);
            List<FieldEntity>fieldEntities =new ArrayList<>();
                for (String fieldCode: cropDTO.getFieldList()){
                    if (fieldDao.existsById(fieldCode)){
                        fieldEntities.add(fieldDao.getReferenceById(fieldCode));
                    }
                }
            cropEntity.setFieldList(fieldEntities);
                for (FieldEntity fieldEntity:fieldEntities){
                    fieldEntity.getCropList().add(cropEntity);
                }

                cropDao.save(cropEntity);
                if (cropEntity==null){
                    throw new DataPersistException("Crop ID not found!");
                }
    }

    @Override
    public void updateCrop(String cropCode, CropDTO cropDTO) {
        Optional<CropEntity> cropEntity = cropDao.findById(cropCode);
        if (cropEntity.isPresent()){
            cropEntity.get().setCropName(cropDTO.getCropName());
            cropEntity.get().setScientificName(cropDTO.getScientificName());
            cropEntity.get().setSeason(cropDTO.getSeason());
            cropEntity.get().setCategory(cropDTO.getCategory());
            cropEntity.get().setCropImage(cropDTO.getCropImage());
        }
    }
    @Override
    public List<CropDTO> getAllCrops() {
        return null;
    }

    @Override
    public CropDTO getCrop(String cropCode) {
        return null;
    }

    @Override
    public void deleteCrop(String cropCode) {

    }




}
