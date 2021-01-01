package mm.pndaza.tipitakaabidan.utils;

public class PaliTextNormalizer {
    private PaliTextNormalizer() {
    }

    public static String normalize(String input) {

        if (input == null) {
            return null;
        }

        String NormalizedText = input;
        if (!MDetect.isUnicode()) {
            NormalizedText = Rabbit.zg2uni(NormalizedText);
        }
        NormalizedText = NormalizedText.replace("\u1040", "\u101D"); // zero to wa
        NormalizedText = NormalizedText.replace("\u1047", "\u101B"); // seven to ra
        NormalizedText = NormalizedText.replace("\u1005\u103B", "\u1008"); // ဈ - ဈ
        NormalizedText = NormalizedText.replace("\u1025\u102E", "\u1026"); // ဦ - ဦ
        NormalizedText = NormalizedText.replace("\u101E\u103C", "\u1029"); // ဩ - ဩ

        NormalizedText = NormalizedText.replace("(\u1015\u1039[\u1015\u1016]\u1031?)\u102C", "$1\u102B"); // ပ္ပာ ပ္ဖာ - ပ္ပါ ပ္ဖါ
        NormalizedText = NormalizedText.replace("(\u1012\u1039[\u1012\u1013]\u1031?)\u102C", "$1\u102B"); // ဒ္ဒာ ဒ္ဓာ - ဒ္ဒါ ဒ္ဓါ
        NormalizedText = NormalizedText.replace("(\u1000\u1039\u1001\u1031?)\u102B", "$1\u102C"); // က္ခါ - က္ခာ


        return NormalizedText;

    }
}
