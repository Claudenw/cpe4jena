/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xenei.cpe.rdf;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import us.springett.parsers.cpe.Cpe;
import us.springett.parsers.cpe.CpeParser;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

public class CPESegmentTest {

	private static final String URN = "cpe:2.3:h:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";

	@Test
	public void extractSegmentTest() throws CpeParsingException {
		Cpe cpe = CpeParser.parse(URN);

		for (CPESegment segment : CPESegment.values()) {
			String value = segment.extractSegment(cpe);
			if (segment == CPESegment.part) {
				assertEquals("h", value);
			} else {
				assertEquals(segment.name(), value);
			}
		}
	}

	@Test
	public void extractWellFormedSegmentTest() throws CpeParsingException {
		Cpe cpe = CpeParser.parse(URN);

		for (CPESegment segment : CPESegment.values()) {
			String value = segment.extractWellFormedSegment(cpe);
			if (segment == CPESegment.part) {
				assertEquals("h", value);
			} else {
				assertEquals(segment.name(), value);
			}
		}
	}
}
