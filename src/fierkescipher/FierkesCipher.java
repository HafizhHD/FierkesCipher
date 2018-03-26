/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fierkescipher;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hafizh Dary <hafizhdary@gmail.com>
 */
public class FierkesCipher {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		/*String apa = "Apa elu tega",
				apa1 = "Ape loe tegs",
				apa2 = "",
				apa3 = "";
		
		for(int i=0;i<apa.length();i++) {
			apa2 += (char)(apa.charAt(i)^apa1.charAt(i));
		}
		System.out.println(apa2);
		
		for(int i=0;i<apa.length();i++) {
			apa3 += (char)(apa2.charAt(i)^apa1.charAt(i));
		}
		System.out.println(apa3);*/
		
		
		String apa = "This adalah tes untuk mengenkripsi dengan FierkesCipher.",
				apa1 = apa;
		
		if(apa1.length()%2!=0) apa1+=(char)0;
		
		FeistelNetwork fn = new FeistelNetwork(false);
		
		for(int i=0;i<16;i++) {
			fn.assignString(apa1,false);
			apa1 = fn.encrypt();
			
			System.out.println(apa1);
			System.out.println("pjg string = " + apa1.length());
		}
		System.out.println("\nOke ini adalah peralihan\n");
		
		//apa1 = apa1.substring(0,apa.length()-1)+(char)(apa1.charAt(apa.length()-1)+1);
		
		fn = new FeistelNetwork(true);
		for(int i=0;i<16;i++) {
			fn.assignString(apa1,true);
			apa1 = fn.decrypt();
			
			System.out.println(apa1);
			System.out.println("pjg string = " + apa1.length());
		}
	}
	
}
