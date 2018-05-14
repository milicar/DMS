
package com.mr.service;

import com.mr.domain.Activity;
import com.mr.domain.Process;
import java.util.List;


public interface ActivityService {

    List<Activity> findAll();
    
    List<Activity> findAllFor(Process parentProcess);
      
    Activity findById(Long id);
    
    Activity save(Activity activity);
    
    void delete(Activity activity);
}
