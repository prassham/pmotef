package com.ibm.pmo.utils;

	public final class Workstream {
	    public final Row rows[];

	    public Row[] getRows() {
			return rows;
		}

		public Workstream(Row[] rows){
	        this.rows = rows;
	    }

	    public static final class Row {
	        public final String key;
	        public String getKey() {
				return key;
			}

			public long getValue() {
				return value;
			}

			public final long value;

	        public Row(String key, long value){
	            this.key = key;
	            this.value = value;
	        }
	    }
	}

