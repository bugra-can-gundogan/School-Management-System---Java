/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_school_management_system;

/**
 *
 * @author bugra
 */
public class DepartmentModel {
    
    private int ID;
    private String Name;
    private int Faculty_ID;

    public DepartmentModel(int ID, String Name, int Faculty_ID) {
        this.ID = ID;
        this.Name = Name;
        this.Faculty_ID = Faculty_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getFaculty_ID() {
        return Faculty_ID;
    }

    public void setFaculty_ID(int Faculty_ID) {
        this.Faculty_ID = Faculty_ID;
    }
    
    
    
    
}
