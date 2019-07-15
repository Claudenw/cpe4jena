package org.xenei.cpe.xml.transform.handlers;

import org.apache.jena.rdf.model.Resource;

/**
 * An interface that describes a handler that can be the subject of a new set of
 * triples.
 *
 */
public interface SubjectHandler {
	/**
	 * Add a triple with this object as the subject.
	 * 
	 * @param p the predicate for the triple.
	 * @param o the object for the triple.
	 */
	public void addTriple(Object p, Object o);

	/**
	 * Get this subject as a resource.
	 * 
	 * @return the resource that represents this subject.
	 */
	public Resource getSubject();
}
