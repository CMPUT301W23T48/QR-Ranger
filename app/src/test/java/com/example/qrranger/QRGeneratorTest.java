package com.example.qrranger;

import static com.example.qrranger.SHA256Hash.hash;

import org.junit.Test;

public class QRGeneratorTest {
    @Test
    /**
     * Gives the hash method a string to hash, and
     * views the output to see if it successfully hashes the same
     * hash provided the same string
     */
    public void SHA256HashTest() {
        String string = "";
        string = hash("apples\n");
        System.out.println(string);
        if(string.equals("39ac5969800f779603237d01dc0be28dd795a33c591af6c7736264482265930a") ){
            System.out.println("Hashing Successful");
        }else{
            System.out.println("Hashing Unsuccessful");
        }
    }
}
