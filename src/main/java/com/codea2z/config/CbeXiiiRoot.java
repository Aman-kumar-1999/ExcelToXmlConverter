package com.codea2z.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import generated.CBEXII;
import generated.CBEXIII;
import generated.ShipmentRequest;

@XmlRootElement(name="CBE-XIII-Root")
public class CbeXiiiRoot {

	 @XmlElement(name = "CBE-XIII")
     protected List<CBEXIII> cbeXiii;

	public List<CBEXIII> getList() {
        if (cbeXiii == null) {
        	cbeXiii = new ArrayList<CBEXIII>();
        }
        return this.cbeXiii;
    }
//	private List<CBEXIII> objects;
//
//    @XmlElement(name = "object")
//    public List<CBEXIII> getObjects() {
//        return objects;
//    }
//
//    public void setObjects(List<CBEXIII> objects) {
//        this.objects = objects;
//    }

}
