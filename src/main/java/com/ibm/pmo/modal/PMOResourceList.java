package com.ibm.pmo.modal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pmo")
public class PMOResourceList {


	private PMOResourceList.Cloudant cloudant;

	public static class Cloudant{

		private String userName;
		public String getUserName() {
			return userName;
		}
		@XmlElement(name="userNmae")
		public void setUserName(String userName) {
			this.userName = userName;
		}

		private String password;
		public String getPassword() {
			return password;
		}
		@XmlElement(name="password")
		public void setPassword(String password) {
			this.password = password;
		}

		private String host;
		public String getHost() {
			return host;
		}
		@XmlElement(name="host")
		public void setHost(String host) {
			System.out.println("Host :"+host);
			this.host = host;
		}

		private String port;
		public String getPort() {
			return port;
		}
		@XmlElement(name="port")
		public void setPort(String port) {
			this.port = port;
		}

		private String url;
		public String getUrl() {
			return url;
		}
		@XmlElement(name="url")
		public void setUrl(String url) {
			this.url = url;
		}

	}

	public PMOResourceList.Cloudant getCloudant() {
		return cloudant;
	}
	@XmlElement(name="cloudant")
	public void setCloudant(PMOResourceList.Cloudant cloudant) {
		this.cloudant = cloudant;
	}
}