// This file was auto-generated by the veyron vdl tool.
// Source: tunnel.vdl
package com.veyron.examples.tunnel;

/**
 * type ShellOpts struct{UsePty bool;Environment []string;Rows uint32;Cols uint32} 
 **/
public final class ShellOpts implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      private boolean usePty;
    
      private java.util.List<java.lang.String> environment;
    
      private int rows;
    
      private int cols;
    

    
    public ShellOpts(final boolean usePty, final java.util.List<java.lang.String> environment, final int rows, final int cols) {
        
            this.usePty = usePty;
        
            this.environment = environment;
        
            this.rows = rows;
        
            this.cols = cols;
        
    }

    
    
    public boolean getUsePty() {
        return this.usePty;
    }
    public void setUsePty(boolean usePty) {
        this.usePty = usePty;
    }
    
    public java.util.List<java.lang.String> getEnvironment() {
        return this.environment;
    }
    public void setEnvironment(java.util.List<java.lang.String> environment) {
        this.environment = environment;
    }
    
    public int getRows() {
        return this.rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public int getCols() {
        return this.cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final ShellOpts other = (ShellOpts)obj;

        
        
        if (this.usePty != other.usePty) {
            return false;
        }
         
        
        
        if (this.environment == null) {
            if (other.environment != null) {
                return false;
            }
        } else if (!this.environment.equals(other.environment)) {
            return false;
        }
         
        
        
        if (this.rows != other.rows) {
            return false;
        }
         
        
        
        if (this.cols != other.cols) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + java.lang.Boolean.valueOf(usePty).hashCode();
        
        result = prime * result + (environment == null ? 0 : environment.hashCode());
        
        result = prime * result + rows;
        
        result = prime * result + cols;
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, usePty);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, environment);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, rows);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, cols);
    	
    }
	public static final android.os.Parcelable.Creator<ShellOpts> CREATOR
		= new android.os.Parcelable.Creator<ShellOpts>() {
		@Override
		public ShellOpts createFromParcel(android.os.Parcel in) {
			return new ShellOpts(in);
		}
		@Override
		public ShellOpts[] newArray(int size) {
			return new ShellOpts[size];
		}
	};
	private ShellOpts(android.os.Parcel in) {
		
			this.usePty = (boolean) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.usePty);
		
			this.environment = (java.util.List<java.lang.String>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.environment);
		
			this.rows = (int) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.rows);
		
			this.cols = (int) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.cols);
		
	}
}