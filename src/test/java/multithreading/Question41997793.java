package multithreading;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Question41997793 {

    public static Result readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        byte[] byteArr = new byte[0];
        byte b = 0;
        int k = 0;
        while ((k = in.readByte()) != 4) {
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
//            if ((k & 0x80) != 128) {
//                break;
//            }
            byteArr = Arrays.copyOf(byteArr, b+1);
            byteArr[b] = b;
            b++;
        }
        return new Result(i, byteArr);
    }

    @Test
    public void test() throws Exception {
        DataInputStream serv_input = new DataInputStream(new ByteArrayInputStream(new byte[] {1, 2, 3, 4}));
        Result result = readVarInt(serv_input);

        int intLength = result.getLength();
        byte[] byteArray = result.getByteArr();

        System.out.println(intLength);
        System.out.println(byteArray);
    }

    static class Result {
        private final int length;
        private final byte[] byteArr;

        Result(int length, byte[] byteArr) {
            this.length = length;
            this.byteArr = byteArr;
        }

        public int getLength() {
            return length;
        }

        public byte[] getByteArr() {
            return byteArr;
        }
    }
}
