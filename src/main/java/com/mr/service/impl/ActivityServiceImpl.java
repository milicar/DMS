package com.mr.service.impl;

import com.mr.dao.ActivityDAO;
import com.mr.domain.Activity;
import com.mr.domain.Process;
import com.mr.service.ActivityDocTypeService;
import com.mr.service.ActivityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDAO activityDAO;
    @Autowired
    ActivityDocTypeService activityDocTypeService;

    @Override
    public List<Activity> findAll() {
        return activityDAO.findAll();
    }

    @Override
    public List<Activity> findAllFor(Process parentProcess) {
        Activity activity = new Activity();
        activity.setParent(parentProcess);
        return activityDAO.findAll(Example.of(activity));
    }

    @Override
    public Activity findById(Long id) {
        return activityDAO.getOne(id);
    }

    @Override
    public Activity save(Activity activity) {
        return activityDAO.save(activity);
    }

    @Override
    public void delete(Activity activity) {
        activityDAO.delete(activity);
    }

}
