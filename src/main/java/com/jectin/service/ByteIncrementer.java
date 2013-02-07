package com.jectin.service;

public class ByteIncrementer {
	private byte[] initialVal;
	private char[] reservedChars = {'$', '&', '+', ',', '/', ':', ';', '=', '?', '@'};

	public ByteIncrementer(byte[] val) {
		initialVal = val;
	}
	public boolean hasReservedChars(byte[] out) {
		for (byte b : out) {
			if(isInReservedChars(b)) {
				return true;
			}
		}
		
		return false;
	}

	public synchronized byte[] incr() {
		int len = initialVal.length;
		
		incrementByteAt(len-1);
		return initialVal;
	}

	private void incrementByteAt(int index) {
		if(index < 0) {
			byte[] newArray = new byte[(initialVal.length + 1)];
			newArray[0] = '0';
			
			for(int i = 0; i < initialVal.length; i++) {
				newArray[i+1] = initialVal[i];
			}
			initialVal = newArray;
		}
		else {
			int currVal = initialVal[index];
			while(currVal < 127) {
				currVal += 1;
				if(false == Character.isLetterOrDigit((char)currVal)) {
					continue;
				}
				else {
					initialVal[index] = (byte)currVal;
					return;
				}
			}
			initialVal[index] = '0';
			incrementByteAt(index - 1);
		}
	}
	private boolean isInReservedChars(byte a) {
		char ca = (char) a;
		for (char rc : reservedChars) {
			if(rc == ca) {
				return true;
			}
		}
		return false;
	}
}
