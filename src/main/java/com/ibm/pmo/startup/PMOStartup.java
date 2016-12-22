package com.ibm.pmo.startup;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import com.ibm.pmo.resources.*;
import com.ibm.pmo.statistics.*;
import com.ibm.pmo.vacation.*;
import com.ibm.pmo.O2Observer.*;
import com.ibm.pmo.employee.*;
import com.ibm.pmo.startoftheweek.*;

public class PMOStartup extends Application{
		    public Set<Class<?>> getClasses() {
		        Set<Class<?>> classes = new HashSet<Class<?>>();
	    // classes.add(PMOResource.class);
		     classes.add(PMOTest.class);
		       classes.add(StatisticsResource.class);
		        classes.add(VacationResource.class);
		      classes.add(EmployeeResource.class);
		      classes.add(EmployeeService.class);
		      classes.add(UtilDisplay.class);
		       classes.add(SowEditData.class);
		       classes.add(viewObserver.class);
		      // classes.add(SowEditData.class);
		        return classes;
		    }
		    
		   
	} 