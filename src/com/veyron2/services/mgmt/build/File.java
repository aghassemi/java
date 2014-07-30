// This file was auto-generated by the veyron vdl tool.
// Source: build.vdl
package com.veyron2.services.mgmt.build;

/**
 * type File struct{Name string;Contents []byte} 
 **/
public final class File {
    
    
      private java.lang.String name;
    
      private java.util.ArrayList<java.lang.Byte> contents;
    

    
    public File(final java.lang.String name, final java.util.ArrayList<java.lang.Byte> contents) {
        
            this.name = name;
        
            this.contents = contents;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.util.ArrayList<java.lang.Byte> getContents() {
        return this.contents;
    }
    public void setContents(java.util.ArrayList<java.lang.Byte> contents) {
        this.contents = contents;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final File other = (File)obj;

        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
        
        
        if (this.contents == null) {
            if (other.contents != null) {
                return false;
            }
        } else if (!this.contents.equals(other.contents)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (contents == null ? 0 : contents.hashCode());
        
        return result;
    }
}