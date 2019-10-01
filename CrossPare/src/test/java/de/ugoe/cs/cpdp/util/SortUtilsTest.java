package de.ugoe.cs.cpdp.util;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

public class SortUtilsTest {

	@Test
	public void testAscending() {
		Double[] inputArray = new Double[] {5.0, 3.0, 10.0, 4.0, 2.0};
		int[] inputIndex = IntStream.range(0, inputArray.length).toArray();
		
		Double[] expectedArray = new Double[] {2.0, 3.0, 4.0, 5.0, 10.0};
		int[] expectedIndex = new int[] {4,1,3,0,2};
		
		SortUtils.quicksort(inputArray, inputIndex);
		
		assertArrayEquals("wrong sorting", expectedArray, inputArray);
		assertArrayEquals("wrong index sorting", expectedIndex, inputIndex);
	}
	
	@Test
	public void testDescending() {
		Double[] inputArray = new Double[] {5.0, 3.0, 10.0, 4.0, 2.0};
		int[] inputIndex = IntStream.range(0, inputArray.length).toArray();
		
		Double[] expectedArray = new Double[] {10.0, 5.0, 4.0, 3.0, 2.0};
		int[] expectedIndex = new int[] {2, 0, 3, 1, 4};
		
		SortUtils.quicksort(inputArray, inputIndex, true);
		
		assertArrayEquals("wrong sorting", expectedArray, inputArray);
		assertArrayEquals("wrong index sorting", expectedIndex, inputIndex);
	}

}
