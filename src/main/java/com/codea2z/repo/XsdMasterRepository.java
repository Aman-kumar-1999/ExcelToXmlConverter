package com.codea2z.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codea2z.model.XsdMaster;


@Repository
public interface XsdMasterRepository  extends JpaRepository<XsdMaster, Long>{

	public XsdMaster findByXsdName(String xsdName);
	
}
