package com.kaselabs.projman.test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {

		File file = new File(".\\data");
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
