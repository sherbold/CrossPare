// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.wekaclassifier;

/**
 * Implementation of ManualDown that classifies that ranks risk by size
 * increasing
 */
public class ManualDown extends AbstractManualUpDown {

	/**
	 * default serialization id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * scoring array for manual up
	 * 
	 * @see AbstractManualUpDown#getScoreArray(double)
	 */
	@Override
	public double[] getScoreArray(double score) {
		return new double[] { score, 1 - score };
	}
}
