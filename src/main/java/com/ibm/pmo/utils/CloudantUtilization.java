package com.ibm.pmo.utils;

import java.util.HashMap;

public  class CloudantUtilization {
	    public  Row rows[];

	    public Row[] getRows() {
			return rows;
		}

		public CloudantUtilization(Row[] rows){
	        this.rows = rows;
	    }

	    public   class Row {
	        public  Key key;
	        public Key getKey() {
				return key;
			}

			public long getValue() {
				return value;
			}

			public  long value;
			public long temp;

	        public Row(Key key, long value){
	            this.key = key;
	            this.value = value;
	        }

	        public   class Key {
	            public  String _id;
	            public String get_id() {
					return _id;
				}

				public String get_rev() {
					return _rev;
				}

				public String getEmp_id() {
					return emp_id;
				}

				public AvailHours getAvailHours() {
					return availHours;
				}

				public Utilization getUtilization() {
					return utilization;
				}

				public  String _rev;
	            public  String emp_id;
	            public  AvailHours availHours;
	            public  Utilization utilization;
	    
	            public Key(String _id, String _rev, String emp_id, AvailHours availHours, Utilization utilization){
	                this._id = _id;
	                this._rev = _rev;
	                this.emp_id = emp_id;
	                this.availHours = availHours;
	                this.utilization = utilization;
	            }
	    	           
	            public class AvailHours
	            {
	                private Float APR;
	                private Float MAY;
	                private Float OCT;
	                private Float NOV;
	                private Float DEC;
	                private Float JAN;
	                private Float JUL;
	                private Float JUN;
	                private Float AUG;
	                private Float SEP;
	                private Float FEB;
	                private Float MAR;
	                
	                public HashMap<String,Float >  getAvailHours(){
	                	HashMap<String, Float> dbMap =  new HashMap<>();
	                	dbMap.put("JAN", this.JAN);
	                	dbMap.put("FEB", this.FEB);
	                	dbMap.put("MAR", this.MAR);
	                	dbMap.put("APR", this.APR);
	                	dbMap.put("MAY", this.MAY);
	                	dbMap.put("JUN", this.JUN);
	                	dbMap.put("JUL", this.JUL);
	                	dbMap.put("AUG", this.AUG);
	                	dbMap.put("SEP", this.SEP);
	                	dbMap.put("OCT", this.OCT);
	                	dbMap.put("NOV", this.NOV);
	                	dbMap.put("DEC", this.DEC);
	                return dbMap;
	                }
	                public Float getAPR ()
	                { return APR;
	                }

	                public void setAPR (Float APR)
	                {
	                    this.APR = APR;
	                }

	                public Float getMAY ()
	                {
	                    return MAY;
	                }

	                public void setMAY (Float MAY)
	                {
	                    this.MAY = MAY;
	                }

	                public Float getOCT ()
	                {
	                    return OCT;
	                }

	                public void setOCT (Float OCT)
	                {
	                    this.OCT = OCT;
	                }

	                public Float getNOV ()
	                {
	                    return NOV;
	                }

	                public void setNOV (Float NOV)
	                {
	                    this.NOV = NOV;
	                }

	                public Float getDEC ()
	                {
	                    return DEC;
	                }

	                public void setDEC (Float DEC)
	                {
	                    this.DEC = DEC;
	                }

	                public Float getJAN ()
	                {
	                    return JAN;
	                }

	                public void setJAN (Float JAN)
	                {
	                    this.JAN = JAN;
	                }

	                public Float getJUL ()
	                {
	                    return JUL;
	                }

	                public void setJUL (Float JUL)
	                {
	                    this.JUL = JUL;
	                }

	                public Float getJUN ()
	                {
	                    return JUN;
	                }

	                public void setJUN (Float JUN)
	                {
	                    this.JUN = JUN;
	                }

	                public Float getAUG ()
	                {
	                    return AUG;
	                }

	                public void setAUG (Float AUG)
	                {
	                    this.AUG = AUG;
	                }

	                public Float getSEP ()
	                {
	                    return SEP;
	                }

	                public void setSEP (Float SEP)
	                {
	                    this.SEP = SEP;
	                }

	                public Float getFEB ()
	                {
	                    return FEB;
	                }

	                public void setFEB (Float FEB)
	                {
	                    this.FEB = FEB;
	                }

	                public Float getMAR ()
	                {
	                    return MAR;
	                }

	                public void setMAR (Float MAR)
	                {
	                    this.MAR = MAR;
	                }

	                @Override
	                public String toString()
	                {
	                    return "ClassPojo [APR = "+APR+", MAY = "+MAY+", OCT = "+OCT+", NOV = "+NOV+", DEC = "+DEC+", JAN = "+JAN+", JUL = "+JUL+", JUN = "+JUN+", AUG = "+AUG+", SEP = "+SEP+", FEB = "+FEB+", MAR = "+MAR+"]";
	                }
	                
	            }
	            
	            public class Utilization
	            {
	                private Float APR;

	                private Float MAY;

	                private Float OCT;

	                private Float NOV;

	                private Float DEC;

	                private Float JAN;

	                private Float JUL;

	                private Float JUN;

	                private Float AUG;

	                private Float SEP;

	                private Float FEB;

	                private Float MAR;

	                public Float getAPR ()
	                {
	                    return APR;
	                }

	                public void setAPR (Float APR)
	                {
	                    this.APR = APR;
	                }

	                public Float getMAY ()
	                {
	                    return MAY;
	                }

	                public void setMAY (Float MAY)
	                {
	                    this.MAY = MAY;
	                }

	                public Float getOCT ()
	                {
	                    return OCT;
	                }

	                public void setOCT (Float OCT)
	                {
	                    this.OCT = OCT;
	                }

	                public Float getNOV ()
	                {
	                    return NOV;
	                }

	                public void setNOV (Float NOV)
	                {
	                    this.NOV = NOV;
	                }

	                public Float getDEC ()
	                {
	                    return DEC;
	                }

	                public void setDEC (Float DEC)
	                {
	                    this.DEC = DEC;
	                }

	                public Float getJAN ()
	                {
	                    return JAN;
	                }

	                public void setJAN (Float JAN)
	                {
	                    this.JAN = JAN;
	                }

	                public Float getJUL ()
	                {
	                    return JUL;
	                }

	                public void setJUL (Float JUL)
	                {
	                    this.JUL = JUL;
	                }

	                public Float getJUN ()
	                {
	                    return JUN;
	                }

	                public void setJUN (Float JUN)
	                {
	                    this.JUN = JUN;
	                }

	                public Float getAUG ()
	                {
	                    return AUG;
	                }

	                public void setAUG (Float AUG)
	                {
	                    this.AUG = AUG;
	                }

	                public Float getSEP ()
	                {
	                    return SEP;
	                }

	                public void setSEP (Float SEP)
	                {
	                    this.SEP = SEP;
	                }

	                public Float getFEB ()
	                {
	                    return FEB;
	                }

	                public void setFEB (Float FEB)
	                {
	                    this.FEB = FEB;
	                }

	                public Float getMAR ()
	                {
	                    return MAR;
	                }

	                public void setMAR (Float MAR)
	                {
	                    this.MAR = MAR;
	                }

	                @Override
	                public String toString()
	                {
	                    return "ClassPojo [APR = "+APR+", MAY = "+MAY+", OCT = "+OCT+", NOV = "+NOV+", DEC = "+DEC+", JAN = "+JAN+", JUL = "+JUL+", JUN = "+JUN+", AUG = "+AUG+", SEP = "+SEP+", FEB = "+FEB+", MAR = "+MAR+"]";
	                }
	                public HashMap<String, Float>  getUtilHours(){
	                	HashMap<String, Float> dbMap =  new HashMap<>();
	                	dbMap.put("JAN", this.JAN);
	                	dbMap.put("FEB", this.FEB);
	                	dbMap.put("MAR", this.MAR);
	                	dbMap.put("APR", this.APR);
	                	dbMap.put("MAY", this.MAY);
	                	dbMap.put("JUN", this.JUN);
	                	dbMap.put("JUL", this.JUL);
	                	dbMap.put("AUG", this.AUG);
	                	dbMap.put("SEP", this.SEP);
	                	dbMap.put("OCT", this.OCT);
	                	dbMap.put("NOV", this.NOV);
	                	dbMap.put("DEC", this.DEC);
	                return dbMap;
	                }
	            }
	            
	        }
	    }
	}

