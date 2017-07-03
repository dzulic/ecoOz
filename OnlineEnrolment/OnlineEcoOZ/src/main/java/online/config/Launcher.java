/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author ciricj
 */
public class Launcher  extends AbstractAnnotationConfigDispatcherServletInitializer {

 
  @Override
    protected Class<?>[] getServletConfigClasses() {
        return null; 
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ EnrolmentConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
