package stream;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Question42225237 {
    class DatiTelematico {
        public DatiTelematico(Adempimento adempimento) {
            this.adempimento = adempimento;
        }

        private Adempimento adempimento;

        public Adempimento getAdempimento() {
            return adempimento;
        }
    }

    class Adempimento {
        public Adempimento(List<DatiNegozio> datiNegozio) {
            this.datiNegozio = datiNegozio;
        }

        private List<DatiNegozio> datiNegozio;

        public List<DatiNegozio> getDatiNegozio() {
            return datiNegozio;
        }

    }

    class DatiNegozio {
        public DatiNegozio(List<Negozio> negozio) {
            this.negozio = negozio;
        }

        private List<Negozio> negozio;

        public List<Negozio> getNegozio() {
            return negozio;
        }
    }

    class Negozio {
        public Negozio(List<Tassazione> tassazione) {
            this.tassazione = tassazione;
        }

        private List<Tassazione> tassazione;

        public List<Tassazione> getTassazione() {
            return tassazione;
        }
    }

    class Tassazione {
        private BigDecimal importo;

        Tassazione(BigDecimal importo) {
            this.importo = importo;
        }

        public BigDecimal getImporto() {
            return importo;
        }

    }


    @Test
    public void testTT() {
        DatiTelematico datiTelematico = new DatiTelematico(
                new Adempimento(
                        ImmutableList.of(
                                new DatiNegozio(
                                        ImmutableList.of(
                                                new Negozio(
                                                        ImmutableList.of(
                                                                new Tassazione(BigDecimal.ONE),
                                                                new Tassazione(BigDecimal.TEN)
                                                        )
                                                ),
                                                new Negozio(
                                                        ImmutableList.of(
                                                                new Tassazione(BigDecimal.ONE)
                                                        )
                                                )
                                        )
                                ),
                                new DatiNegozio(
                                        ImmutableList.of(
                                                new Negozio(
                                                        ImmutableList.of(
                                                                new Tassazione(BigDecimal.TEN)
                                                        )
                                                ),
                                                new Negozio(
                                                        ImmutableList.of(
                                                                new Tassazione(BigDecimal.ONE)
                                                        )
                                                )
                                        )
                                )

                        )
                )
        );
        BigDecimal dd =Optional.of(datiTelematico)
                .map(DatiTelematico::getAdempimento)
                .map(Adempimento::getDatiNegozio)
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .map(DatiNegozio::getNegozio)
                .flatMap(Collection::stream)
                .map(Negozio::getTassazione)
                .flatMap(Collection::stream)
                .map(Tassazione::getImporto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(dd);
    }

    public static void main(String[] args) {

        int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12,           4,
                62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17,  9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };


        String text = "00000001 00100011 01000101 01100111 10001001 10101011 11001101 11101111".replace(" ", "");
        System.out.println(text);
        text = Permute(text,IP);
        System.out.println(text);
        text = Undo(text,IP);
        System.out.println(text);
    }

    public static String   Permute(String text,int [] table )
    {
        char[] chars = new char[table.length];
        for (int i = 0 ; i < table.length; i++) {
            chars[i] = text.charAt(table[i]-1);
        }

        return new String(chars);
    }

    public static  String Undo(String text,int [] table ) {
        char[] chars = new char[table.length];
        for (int i = 0; i < table.length; i++)
        {
            chars[table[i]-1] = text.charAt(i);
        }

        return new String(chars);
    }

}
