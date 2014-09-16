// This file was auto-generated by the veyron vdl tool.
// Source: schema.vdl
package io.veyron.examples.mdb.schema;

/**
 * type Person struct{Image string;Name string;BirthDate int64} 
 * Person represents a person, in any role, including producers, director,
 * actor, etc.
 **/
public final class Person implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Image")
      private java.lang.String image;
    
      @com.google.gson.annotations.SerializedName("Name")
      private java.lang.String name;
    
      @com.google.gson.annotations.SerializedName("BirthDate")
      private long birthDate;
    

    
    public Person(final java.lang.String image, final java.lang.String name, final long birthDate) {
        
            this.image = image;
        
            this.name = name;
        
            this.birthDate = birthDate;
        
    }

    
    
    public java.lang.String getImage() {
        return this.image;
    }
    public void setImage(java.lang.String image) {
        this.image = image;
    }
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public long getBirthDate() {
        return this.birthDate;
    }
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final Person other = (Person)obj;

        
        
        
        if (this.image == null) {
            if (other.image != null) {
                return false;
            }
        } else if (!this.image.equals(other.image)) {
            return false;
        }
         
         
        
        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
         
        
        
        
        if (this.birthDate != other.birthDate) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (image == null ? 0 : image.hashCode());
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + java.lang.Long.valueOf(birthDate).hashCode();
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, image);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, birthDate);
    	
    }
	public static final android.os.Parcelable.Creator<Person> CREATOR
		= new android.os.Parcelable.Creator<Person>() {
		@Override
		public Person createFromParcel(android.os.Parcel in) {
			return new Person(in);
		}
		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
	private Person(android.os.Parcel in) {
		
			this.image = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.image);
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.birthDate = (long) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.birthDate);
		
	}
}