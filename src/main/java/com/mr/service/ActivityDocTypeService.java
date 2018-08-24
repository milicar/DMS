
package com.mr.service;

import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import java.util.List;


public interface ActivityDocTypeService {
    
    List<ActivityDocumentType> findAll();
    
    List<ActivityDocumentType> findAllFor(Activity activity);
    
    List<ActivityDocumentType> findAllFor(Activity activity, ActivityDocumentType.Direction direction);
    
    List<ActivityDocumentType> findAllFor(DocumentType documentType);
    
    ActivityDocumentType save(ActivityDocumentType activityDocumentType);
    
    ActivityDocumentType save(Activity activity, DocumentType documentType, ActivityDocumentType.Direction direction);
    
    void delete(ActivityDocumentType activityDocumentType);
    
}
