package com.codea2z.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import generated.CBEXII;
import generated.ShipmentRequest;

@XmlRootElement(name="CBE-XII-Root")
public class CbeXiiRoot {

	 @XmlElement(name = "CBE-XII")
     protected List<CBEXII> cbeXii;

	public List<CBEXII> getList() {
        if (cbeXii == null) {
        	cbeXii = new ArrayList<CBEXII>();
        }
        return this.cbeXii;
    }

}
