package com.mr.service.impl;

import com.mr.dao.ActivityDocTypeDAO;
import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.service.ActivityDocTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ActivityDocTypeServiceImpl implements ActivityDocTypeService {

    @Autowired
    ActivityDocTypeDAO activityDocTypeDAO;
    
    @Override
    public List<ActivityDocumentType> findAll() {
        return activityDocTypeDAO.findAll();
    }

    @Override
    public List<ActivityDocumentType> findAllFor(Activity activity) {
        ActivityDocumentType adt = new ActivityDocumentType();
        adt.setActivity(activity);
        return activityDocTypeDAO.findAll(Example.of(adt));
    }

    @Override
    public List<ActivityDocumentType> findAllFor(Activity activity, ActivityDocumentType.Direction direction) {
        ActivityDocumentType adt = new ActivityDocumentType();
        adt.setActivity(activity);
        adt.setDirection(direction);
        return activityDocTypeDAO.findAll(Example.of(adt));
    }

    @Override
    public List<ActivityDocumentType> findAllFor(DocumentType documentType) {
        ActivityDocumentType adt = new ActivityDocumentType();
        adt.setDocumentType(documentType);
        return activityDocTypeDAO.findAll(Example.of(adt));
    }

    @Override
    public ActivityDocumentType save(ActivityDocumentType activityDocumentType) {
        return activityDocTypeDAO.save(activityDocumentType);
    }
    
    @Override
    public ActivityDocumentType save(Activity activity, DocumentType documentType, ActivityDocumentType.Direction direction){
        ActivityDocumentType adt = new ActivityDocumentType();
        adt.setActivity(activity);
        adt.setDocumentType(documentType);
        adt.setDirection(direction);
        return activityDocTypeDAO.save(adt);
    }

    @Override
    public void delete(ActivityDocumentType activityDocumentType) {
        activityDocTypeDAO.delete(activityDocumentType);
    }

}
