package com.mr;

import com.mr.dao.CompanyDAO;
import com.mr.dao.DocumentDAO;
import com.mr.dao.DocumentDescriptorDAO;
import com.mr.dao.DocumentTypeDAO;
import com.mr.dao.DocumentTypeDescriptorDAO;
import com.mr.dao.ProcessDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class DMSApplication extends SpringBootServletInitializer implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DMSApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DMSApplication.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.xhtml");
    }

    @Autowired
    DocumentTypeDAO dtypeDao;
    @Autowired
    DocumentTypeDescriptorDAO dtdDao;
    @Autowired
    DocumentDAO docDao;
    @Autowired
    DocumentDescriptorDAO ddDao;
    @Autowired
    private ProcessDAO processDao;
    @Autowired
    private CompanyDAO companyDAO;
    
    @Override
    public void run(String... args) throws Exception {    
 
        
    }
}

