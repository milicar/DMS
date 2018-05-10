package com.mr.dao;

import com.mr.domain.ActivityDocTypeId;
import com.mr.domain.ActivityDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityDocTypeDAO extends JpaRepository<ActivityDocumentType, ActivityDocTypeId>{

}
