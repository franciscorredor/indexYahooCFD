package com.wireless.soft.indices.cfd.deserializable.json.object;

import java.io.Serializable;

/**
 * Objeto POJO, que contiene la informacion de Yahoo finanzas
 * @author Francisco
 * REF:
 * 	http://jsonviewer.stack.hu/#http://finance.yahoo.com/webservice/v1/symbols/AMZN/quote?format=json
 *
 */
public class ReturnIndexYahooFinanceObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -530614290559982762L;

	// ////////////////////////////////////////////////////////////////////////
	// Atributos de la clase
	// ////////////////////////////////////////////////////////////////////////

	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	@Override
    public String toString() {
	StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
	if (this.list !=null){
		s.append(" list [" + this.list.toString() + "]");
	}

	return s.toString();
    }

	public class List {

		private Meta meta;
		
		private Resource[] resources;

		public Meta getMeta() {
			return meta;
		}

		public void setMeta(Meta meta) {
			this.meta = meta;
		}
		
		@Override
	    public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
		if (this.meta !=null){
			s.append(" meta [" + this.meta.toString() + "]");
		}

		return s.toString();
	    }
		


		public class Meta {

			private String type;
			
			private String start;

			private String count;

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getStart() {
				return start;
			}

			public void setStart(String start) {
				this.start = start;
			}

			public String getCount() {
				return count;
			}

			public void setCount(String count) {
				this.count = count;
			}
			
			@Override
		    public String toString() {
			StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
			s.append(" type [" + this.type + "]");
			s.append(" start [" + this.start + "]");
			s.append(" count [" + this.count + "]");

			return s.toString();
		    }

		}
		
		public class Resource implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7988773852570114427L;
			
		}
		
		 

	}

}
