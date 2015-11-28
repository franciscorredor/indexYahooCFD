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
		s.append("\n list [" + this.list.toString() + "]");
	}

	return s.toString();
    }

	public class List {

		private Meta meta;
		
		private Resources[] resources;

		public Meta getMeta() {
			return meta;
		}

		public void setMeta(Meta meta) {
			this.meta = meta;
		}
		
		@Override
	    public String toString() {
		StringBuffer s = new StringBuffer();
		if (this.meta !=null){
			s.append("\n meta [" + this.meta.toString() + "]");
		}
		if (null != resources && resources.length > 0) {
		    for (int i = 0; i < resources.length; i++) {
			s.append("\n resources{" + i + "} [" + this.resources[i].toString() + "]");
		    }

		} else {
		    s.append("  El arreglo no tiene resources  ");
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
			StringBuffer s = new StringBuffer();
			s.append("\n type [" + this.type + "]");
			s.append(" start [" + this.start + "]");
			s.append(" count [" + this.count + "]");

			return s.toString();
		    }

		}
		
		public class Resources{
			
			
			private Resource resource;
			
			public Resource getResource() {
				return resource;
			}



			public void setResource(Resource resource) {
				this.resource = resource;
			}
			
			@Override
		    public String toString() {
			StringBuffer s = new StringBuffer();
			if (this.resource !=null){
				s.append("\n resource [" + this.resource.toString() + "]");
			}

			return s.toString();
		    }
			
			
			
		public class Resource{	

			private String classname;
			
			private Fields fields;

			public String getClassname() {
				return classname;
			}

			public void setClassname(String classname) {
				this.classname = classname;
			}
			
			

			
			public Fields getFields() {
				return fields;
			}

			public void setFields(Fields fields) {
				this.fields = fields;
			}

			
			public class Fields{
				
				private String name;
				private String price;
				private String symbol;
				private String ts;
				private String type;
				private String utctime;
				private String volume;
				
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getPrice() {
					return price;
				}
				public void setPrice(String price) {
					this.price = price;
				}
				public String getSymbol() {
					return symbol;
				}
				public void setSymbol(String symbol) {
					this.symbol = symbol;
				}
				public String getTs() {
					return ts;
				}
				public void setTs(String ts) {
					this.ts = ts;
				}
				public String getType() {
					return type;
				}
				public void setType(String type) {
					this.type = type;
				}
				public String getUtctime() {
					return utctime;
				}
				public void setUtctime(String utctime) {
					this.utctime = utctime;
				}
				public String getVolume() {
					return volume;
				}
				public void setVolume(String volume) {
					this.volume = volume;
				}
				
				@Override
			    public String toString() {
				StringBuffer s = new StringBuffer();
				s.append("\n name [" + this.name + "]");
				s.append("\n price [" + this.price + "]");
				s.append("\n symbol [" + this.symbol + "]");
				
				s.append("\n ts [" + this.ts + "]");
				s.append("\n type [" + this.type + "]");
				s.append("\n utctime [" + this.utctime + "]");
				
				s.append("\n volume [" + this.volume + "]");
				
				return s.toString();
			    }	
				
				
			}
			
			@Override
		    public String toString() {
			StringBuffer s = new StringBuffer();
			s.append("\n classname [" + this.classname + "]");
			if (this.fields !=null){
				s.append("\n fields [" + this.fields.toString() + "]");
			}

			return s.toString();
		    }
		}



		
			
		}
		
		 

	}

}
