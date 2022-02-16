package org.quevedo.secretkeeper.security.asimetrical;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;
import org.quevedo.secretkeeper.security.utils.SecurityConsts;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

@Log4j2
public class CipherAssimetrical {
    public Either<String, String> cipherAsimetrically(String toCipher, Key key) {
        Either<String, String> result;
        try {
            Cipher cipher = Cipher.getInstance(SecurityConsts.ASIM_CIPHER_ALGORITHM);
            byte[] buffer;
            buffer = toCipher.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bufferCiphered = cipher.doFinal(buffer);
            byte[] base64Buffer = Base64.getUrlEncoder().encode(bufferCiphered);
            result = Either.right(new String(base64Buffer));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
            result = Either.left(GuiConsts.MSG_ERROR_CIPHER);
        }
        return result;
    }

    public Either<String, String> uncipherAsimetrically(String toDecipher, PrivateKey key) {
        Either<String, String> result;
        try {
            Cipher cipher = Cipher.getInstance(SecurityConsts.ASIM_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] buffer = Base64.getUrlDecoder().decode(toDecipher);
            byte[] decipherBuffer = cipher.doFinal(buffer);
            result = Either.right(new String(decipherBuffer, StandardCharsets.UTF_8));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
            result = Either.left(GuiConsts.MSG_ERROR_UNCIPHER);
        }
        return result;
    }
}
