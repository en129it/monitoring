package com.ddv.graph.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;

public class Utils {

	public static int[] extract(int[] anArray, int anArrayElem) {
		int[] rslt = new int[anArray.length-1];
		
		int pos = 0;
		for (int i=0; i<anArray.length; i++) {
			if (anArray[i]!=anArrayElem) {
				rslt[pos++] = anArray[i];
			}
		}
		return rslt;
	}
	
	public static int[] prefix(int aFirstArrayElem, int[] anArray) {
		int[] rslt = new int[anArray.length+1];
		rslt[0] = aFirstArrayElem;
		System.arraycopy(anArray, 0, rslt, 1, anArray.length);
		return rslt;
	}
	
	public static List<int[]> generateAllCombinations(int aLength) {
		int[] array = new int[aLength];
		for (int i=0; i<aLength; i++) {
			array[i] = i;
		}
		
		ArrayList<int[]> rslt = new ArrayList<int[]>();
		generateAllCombinations(array, rslt);
		return rslt;
	}
	
	private static void generateAllCombinations(int[] anArray, ArrayList<int[]> anOut) {
		if (anArray.length==2) {
			anOut.add(new int[]{ anArray[0], anArray[1]});
			anOut.add(new int[]{ anArray[1], anArray[0]});
		} else {
			for (int i=0; i<anArray.length; i++) {
				ArrayList<int[]> out = new ArrayList<int[]>();
				generateAllCombinations(extract(anArray, anArray[i]), out);
				for (int[] outItem : out) {
					anOut.add(prefix(anArray[i], outItem));
				}
			}
		}
	}
	
	public static <T> List<T> reorder(List<T> aList, int[] aSortIndexes) {
		ArrayList<T> rslt = new ArrayList<T>(aList.size());
		
		for (int i=0; i<aSortIndexes.length; i++) {
			rslt.add(aList.get(aSortIndexes[i]));
		}
		
		return rslt;
	}
	
	public static <T> boolean areStrictEquals(List<T> aFirst, List<T> aSecond) {
		int firstListSize = aFirst.size(); 
		if (firstListSize==aSecond.size()) {
			for (int i=0; i<firstListSize; i++) {
				if (aFirst.get(i)!=aSecond.get(i)) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
}
