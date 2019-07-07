package org.xenei.cpe.xml.transform.handlers;

import org.apache.jena.rdf.model.Resource;

public interface SubjectHandler {

	public void addTriple( Object p, Object o);
	public Resource getSubject();
}
