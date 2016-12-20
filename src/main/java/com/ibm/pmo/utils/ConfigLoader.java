package com.ibm.pmo.utils;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.ibm.pmo.modal.PMOResourceList;

public class ConfigLoader {


	private static ConfigLoader configLoader = null;
	private PMOResourceList config;

	private ConfigLoader() throws Exception{

		try{
			JAXBContext context = JAXBContext.newInstance(PMOResourceList.class);
	        Unmarshaller un = context.createUnmarshaller();
	        config = (PMOResourceList) un.unmarshal(getClass().getClassLoader().getResourceAsStream(ResourceConstants.RESOURCE_CONFIG_XML_FILE));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

	public PMOResourceList getConfig() {
		return config;
	}

	public static void createConfigLoaderInsatnce() throws Exception{
		if(configLoader ==null) {
			configLoader = new ConfigLoader();
		}
	}

	public static ConfigLoader getConfigLoaderInstance() throws Exception {
		if(configLoader ==null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}


}