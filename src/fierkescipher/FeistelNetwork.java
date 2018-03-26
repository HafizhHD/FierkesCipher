/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fierkescipher;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *
 * @author Hafizh Dary <hafizhdary@gmail.com>
 */
public class FeistelNetwork {
	private String left = "";
	private String right = "";
	private final int[] rowtrp = new int[] {3,1,0,2};
	private final int[] coltrp = new int[] {2,0,3,1};
	private int[][] sBoxKey;
	private int[][] sBox1;
	
	public FeistelNetwork(boolean isDecrypt) {
		sBoxKey = new int[][]
		{{183, 59, 68, 98, 83, 6, 214, 23, 10, 157, 174, 173, 202, 166, 70, 85},
		{7, 137, 163, 69, 221, 63, 177, 210, 132, 178, 244, 18, 242, 237, 100, 228},
		{95, 80, 58, 43, 161, 99, 187, 64, 61, 110, 117, 33, 22, 1, 19, 66},
		{220, 230, 209, 30, 118, 184, 175, 205, 109, 196, 113, 169, 129, 253, 65, 55},
		{128, 49, 238, 182, 48, 193, 243, 146, 152, 81, 96, 247, 190, 153, 232, 188},
		{131, 160, 208, 147, 36, 212, 107, 46, 37, 3, 56, 151, 240, 162, 9, 97},
		{168, 254, 122, 201, 142, 20, 180, 211, 86, 34, 171, 165, 179, 75, 215, 133},
		{140, 150, 8, 130, 74, 41, 218, 194, 185, 106, 21, 25, 0, 62, 90, 108},
		{144, 156, 54, 28, 200, 60, 235, 155, 57, 35, 186, 248, 217, 189, 112, 47},
		{24, 125, 103, 111, 223, 206, 127, 16, 116, 172, 236, 170, 102, 104, 195, 138},
		{12, 123, 213, 13, 250, 32, 203, 241, 149, 120, 191, 114, 234, 17, 42, 105},
		{53, 245, 29, 207, 231, 93, 229, 50, 135, 252, 164, 255, 124, 121, 45, 115},
		{139, 15, 141, 89, 192, 199, 233, 87, 239, 51, 71, 145, 84, 26, 77, 52},
		{198, 27, 94, 72, 67, 2, 4, 40, 204, 88, 251, 148, 143, 5, 158, 159},
		{219, 249, 176, 11, 79, 167, 91, 101, 92, 225, 73, 136, 39, 154, 38, 216},
		{126, 82, 226, 181, 134, 14, 224, 222, 227, 31, 119, 197, 44, 246, 76, 78}};
		
		sBox1 = new int[][]
		{{179, 219, 159, 217, 58, 234, 113, 62, 240, 199, 115, 232, 24, 72, 13, 83},
		{130, 195, 16, 221, 70, 175, 64, 200, 231, 11, 119, 101, 138, 167, 92, 17},
		{205, 239, 136, 32, 106, 152, 27, 137, 6, 111, 206, 30, 196, 154, 73, 9},
		{208, 71, 112, 144, 45, 51, 123, 63, 117, 25, 96, 120, 104, 81, 94, 93},
		{220, 249, 61, 86, 170, 108, 23, 246, 56, 215, 190, 156, 98, 77, 211, 100},
		{202, 52, 80, 125, 183, 75, 60, 242, 49, 187, 103, 177, 161, 78, 176, 250},
		{127, 79, 227, 168, 180, 174, 54, 192, 66, 19, 253, 3, 129, 160, 107, 46},
		{82, 171, 226, 65, 173, 21, 237, 147, 207, 203, 109, 165, 28, 105, 121, 134},
		{29, 186, 74, 172, 252, 204, 218, 131, 26, 8, 201, 85, 181, 158, 2, 122},
		{210, 126, 251, 146, 163, 69, 216, 182, 43, 224, 244, 139, 22, 37, 59, 88},
		{141, 1, 185, 89, 145, 150, 143, 133, 90, 68, 39, 247, 230, 33, 149, 213},
		{114, 178, 169, 255, 55, 36, 155, 132, 57, 50, 124, 153, 140, 5, 162, 135},
		{197, 233, 67, 189, 223, 193, 188, 238, 12, 35, 228, 20, 110, 44, 34, 15},
		{254, 47, 42, 236, 97, 118, 116, 243, 48, 235, 191, 142, 151, 91, 128, 209},
		{241, 41, 102, 7, 4, 87, 18, 212, 222, 198, 14, 53, 38, 248, 84, 0},
		{76, 245, 95, 40, 148, 157, 184, 164, 166, 229, 99, 10, 31, 225, 194, 214}};
		
		if(isDecrypt) {
			for(int i=0;i<sBoxKey.length*sBoxKey[0].length;i++) {
				sBoxKey[i/16][i%16] = (sBoxKey[i/16][i%16]+1)%256;
			}
			for(int i=0;i<16;i++) {
				shiftSBoxRight(sBoxKey);
				shiftSBoxDown(sBox1);
			}
		}
	}
	
	public void assignString(String s, boolean isDecrypt){
		if(!isDecrypt || (isDecrypt && s.length()%2==0)) {
			left = s.substring(0, s.length()/2);
			right = s.substring(s.length()/2);
		}
		else {
			left = s.substring(0, s.length()/2+1);
			right = s.substring(s.length()/2+1);
		}
	}
	
	public String encrypt(){
		String tempLeft = left;
		left = right;
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		xOREncryptKey();
		shiftSBoxRight(sBoxKey);
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		substitutionEncrypt();
		/*System.out.println("{");
		for(int i=0;i<16;i++) {
			System.out.println(Arrays.toString(sBox1[i]));
		}
		System.out.println("}\n\n");*/
		shiftSBoxDown(sBox1);
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		rowTransposition();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		columnTransposition();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		right = xOREncryptLeft(tempLeft);
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		return left+right;
	}
	
	public String decrypt() {
		String tempRight = right,
				tempLeft = left;
		right = left;
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		shiftSBoxLeft(sBoxKey);
		xOREncryptKey();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		shiftSBoxUp(sBox1);
		/*System.out.println("{");
		for(int i=0;i<16;i++) {
			System.out.println(Arrays.toString(sBox1[i]));
		}
		System.out.println("}\n\n");*/
		substitutionEncrypt();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		rowTransposition();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		columnTransposition();
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		left = xOREncryptLeft(tempRight);
		//System.out.println(right);
		//System.out.println("Pjg string = " + right.length());
		right = tempLeft;
		return left+right;
	}
	
	private void xOREncryptKey() {
		int[] key = new int[8];
		System.arraycopy(sBoxKey[0], 0, key, 0, 8);
		String right1 = "";
		int i=0;
		while(i<right.length()) {
			right1+=(char)(right.charAt(i)^key[i%8]);
			i++;
		}
		right = right1;
	}
	
	private String xOREncryptLeft(String tmp) {
		String right1 = "";
		int i=0;
		while(i<right.length()) {
			right1+=(char)(right.charAt(i)^tmp.charAt(i));
			i++;
		}
		return right1;
	}
	
	private void substitutionEncrypt() {
		String right1 = "";
		for(int i=0;i<right.length();i++) {
			int x = right.charAt(i)/16;
			int y = right.charAt(i)%16;
			right1 += (char)sBox1[x][y];
		}
		right = right1;
	}
	
	private void rowTransposition() {
		String right1 = "";
		for(int i=0;i<right.length();i+=16) {
			if(right.length()-i>=16) {
				for(int j=0;j<rowtrp.length;j++) {
					right1+=right.substring(i+4*rowtrp[j],i+4*(rowtrp[j]+1));
				}
			}
			else right1+=right.substring(i);
		}
		right = right1;
	}
	
	private void columnTransposition() {
		String right1 = "";
		for(int i=0;i<right.length();i+=16) {
			if(right.length()-i>=16) {
				for(int k=0;k<4;k++) {
					for(int j=0;j<coltrp.length;j++) {
						right1+=right.charAt(k%4+coltrp[j]);
					}
				}
			}
			else right1+=right.substring(i);
		}
		right = right1;
	}
	
	private void shiftSBoxRight(int[][] sb) {
		int temp = sb[sb.length-1][sb[0].length-1];
		for(int i=sb.length*sb[0].length-2;i>=0;i--) {
			sb[(i+1)/16][(i+1)%16] = sb[i/16][i%16];
		}
		sb[0][0] = temp;
	}
	
	private void shiftSBoxLeft(int[][] sb) {
		int temp = sb[0][0];
		for(int i=0;i<sb.length*sb[0].length-1;i++) {
			sb[i/16][i%16] = sb[(i+1)/16][(i+1)%16];
		}
		sb[sb.length-1][sb[0].length-1] = temp;
	}
	
	private void shiftSBoxDown(int[][] sb) {
		int temp = sb[sb.length-1][sb[0].length-1];
		for(int i=sb.length*sb[0].length-2;i>=0;i--) {
			sb[(i+1)%16][(i+1)/16] = sb[i%16][i/16];
		}
		sb[0][0] = temp;
	}
	
	private void shiftSBoxUp(int[][] sb) {
		int temp = sb[0][0];
		for(int i=0;i<sb.length*sb[0].length-1;i++) {
			sb[i%16][i/16] = sb[(i+1)%16][(i+1)/16];
		}
		sb[sb.length-1][sb[0].length-1] = temp;
	}
}
