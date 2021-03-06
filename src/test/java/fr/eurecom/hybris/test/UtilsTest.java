/**
 * Copyright (C) 2013 EURECOM (www.eurecom.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.eurecom.hybris.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.junit.Test;

import fr.eurecom.hybris.Utils;

public class UtilsTest extends HybrisAbstractTest {

    @Test
    public void testEncryption() {

        byte[] value = new byte[500];
        this.random.nextBytes(value);

        // AES allows 128, 192 or 256 bit key length, that is 16, 24 or 32 byte.
        // With CFB mode of operation, the cipherText has the same length as the plainText.

        // key length = 128 bit (16 byte)
        int keyLength = 16;
        byte[] key = new byte[keyLength];
        byte[] iv = new byte[16];
        this.random.nextBytes(key);
        this.random.nextBytes(iv);
        byte[] clearText = null, cipherText = null;
        try {
            cipherText = Utils.encrypt(value, key, iv);
            clearText = Utils.decrypt(cipherText, key, iv);
            assertArrayEquals(value, clearText);
        } catch(GeneralSecurityException | UnsupportedEncodingException ge) {
            ge.printStackTrace();
            fail();
        }

        // key length = 192 bit (24 byte)
        keyLength = 24;
        key = new byte[keyLength];
        this.random.nextBytes(key);
        clearText = null; cipherText = null;
        try {
            cipherText = Utils.encrypt(value, key, iv);
            clearText = Utils.decrypt(cipherText, key, iv);
            assertArrayEquals(value, clearText);
        } catch(GeneralSecurityException | UnsupportedEncodingException ge) {
            ge.printStackTrace();
            fail();
        }

        // key length = 256 bit (32 byte)
        keyLength = 32;
        key = new byte[keyLength];
        this.random.nextBytes(key);
        clearText = null; cipherText = null;
        try {
            cipherText = Utils.encrypt(value, key, iv);
            clearText = Utils.decrypt(cipherText, key, iv);
            assertArrayEquals(value, clearText);
        } catch(GeneralSecurityException | UnsupportedEncodingException ge) {
            ge.printStackTrace();
            fail();
        }

        // wrong key length
        keyLength = 13;
        key = new byte[keyLength];
        this.random.nextBytes(key);
        clearText = null; cipherText = null;
        try {
            cipherText = Utils.encrypt(value, key, iv);
            clearText = Utils.decrypt(cipherText, key, iv);
            fail();
            assertArrayEquals(value, clearText);
        } catch(GeneralSecurityException | UnsupportedEncodingException ge) {
        }
    }
}
