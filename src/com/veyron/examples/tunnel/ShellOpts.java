// This file was auto-generated by the veyron vdl tool.
// Source: tunnel.vdl
package com.veyron.examples.tunnel;

/**
 * type ShellOpts struct{UsePty bool;Environment []string;Rows uint32;Cols uint32} 
 **/
public final class ShellOpts {
    
    
      private boolean usePty;
    
      private java.util.ArrayList<java.lang.String> environment;
    
      private int rows;
    
      private int cols;
    

    
    public ShellOpts(final boolean usePty, final java.util.ArrayList<java.lang.String> environment, final int rows, final int cols) {
        
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
    
    public java.util.ArrayList<java.lang.String> getEnvironment() {
        return this.environment;
    }
    public void setEnvironment(java.util.ArrayList<java.lang.String> environment) {
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
}