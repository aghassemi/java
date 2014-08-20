// This file was auto-generated by the veyron vdl tool.
// Source: node.vdl
package com.veyron2.services.mgmt.node;

/**
 * type Description struct{Profiles set[string]} 
 * Description enumerates the profiles that a Node supports.
 **/
public final class Description {
    
    
      private java.util.HashSet<java.lang.String> profiles;
    

    
    public Description(final java.util.HashSet<java.lang.String> profiles) {
        
            this.profiles = profiles;
        
    }

    
    
    public java.util.HashSet<java.lang.String> getProfiles() {
        return this.profiles;
    }
    public void setProfiles(java.util.HashSet<java.lang.String> profiles) {
        this.profiles = profiles;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Description other = (Description)obj;

        
        
        if (this.profiles == null) {
            if (other.profiles != null) {
                return false;
            }
        } else if (!this.profiles.equals(other.profiles)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (profiles == null ? 0 : profiles.hashCode());
        
        return result;
    }
}