package mm.pndaza.tipitakaabidan.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by saturngod on 23/1/15.
 * Modified by pndaza on 23/7/19.
 */
public class Rabbit {

    public static String uni2zg(String input) {

        Map<String, String> rule = new LinkedHashMap<>();
        rule.put("\u1004\u103a\u1039", "\u1064");
        rule.put("\u1039\u1010\u103d", "\u1096");
        rule.put("\u102b\u103a", "\u105a");
        rule.put("\u100b\u1039\u100c", "\u1092");
        rule.put("\u102d\u1036", "\u108e");
        rule.put("\u104e\u1004\u103a\u1038", "\u104e");
        rule.put("[\u1025\u1009](?=[\u1039\u102f\u1030])", "\u106a");
        rule.put("[\u1025\u1009](?=[\u1037]?[\u103a])", "\u1025");
        rule.put("\u100a(?=[\u1039\u103d])", "\u106b");
        rule.put("(\u1039[\u1000-\u1021])(\u102D){0,1}\u102f", "$1$2\u1033");
        rule.put("(\u1039[\u1000-\u1021])\u1030", "$1\u1034");
        rule.put("\u1014(?=[\u102d\u102e]?[\u1030\u103d\u103e\u102f\u1039])", "\u108f");
        rule.put("\u1014\u103c", "\u108f\u103c");
        rule.put("\u1039\u1000", "\u1060");
        rule.put("\u1039\u1001", "\u1061");
        rule.put("\u1039\u1002", "\u1062");
        rule.put("\u1039\u1003", "\u1063");
        rule.put("\u1039\u1005", "\u1065");
        rule.put("\u1039\u1006", "\u1066");
        rule.put("\u1039\u1007", "\u1068");
        rule.put("\u1039\u1008", "\u1069");
        rule.put("\u1039\u100b", "\u106c");
        rule.put("\u1039\u100c", "\u106d");
        rule.put("\u100d\u1039\u100d", "\u106e");
        rule.put("\u100d\u1039\u100e", "\u106f");
        rule.put("\u1039\u100f", "\u1070");
        rule.put("\u1039\u1010", "\u1071");
        rule.put("\u1039\u1011", "\u1073");
        rule.put("\u1039\u1012", "\u1075");
        rule.put("\u1039\u1013", "\u1076");
        rule.put("\u1039[\u1014\u108f]", "\u1077");
        rule.put("\u1039\u1015", "\u1078");
        rule.put("\u1039\u1016", "\u1079");
        rule.put("\u1039\u1017", "\u107a");
        rule.put("\u1039\u1018", "\u107b");
        rule.put("\u1039\u1019", "\u107c");
        rule.put("\u1039\u101c", "\u1085");
        rule.put("\u103f", "\u1086");
        rule.put("\u103d\u103e", "\u108a");
        rule.put("(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u102d", "$2$3\u108b");
        rule.put("(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u102e", "$2$3\u108c");
        rule.put("(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u1036", "$2$3\u108d");
        rule.put("(\u1064)([\u1000-\u1021\u1040-\u1049])([\u103b\u103c]?)([\u1031]?)", "$2$3$4$1");
        rule.put("\u101b(?=([\u102d\u102e]?)[\u102f\u1030\u103d\u108a])", "\u1090");
        rule.put("\u100f\u1039\u100d", "\u1091");
        rule.put("\u100b\u1039\u100b", "\u1097");
        rule.put("([\u1000-\u1021\u108f\u1029\u106e\u106f\u1086\u1090\u1091\u1092\u1097])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085\u108a])?([\u103b-\u103e]*)?\u1031",
                "\u1031$1$2$3");
        rule.put("\u103c\u103e", "\u103c\u1087");
        rule.put("([\u1000-\u1021\u108f\u1029])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085])?(\u103c)",
                "$3$1$2");
        rule.put("\u103a", "\u1039");
        rule.put("\u103b", "\u103a");
        rule.put("\u103c", "\u103b");
        rule.put("\u103d", "\u103c");
        rule.put("\u103e", "\u103d");
        rule.put("([^\u103a\u100a])\u103d([\u102d\u102e]?)\u102f", "$1\u1088$2");
        rule.put("([\u101b\u103a\u103c\u108a\u1088\u1090])([\u1030\u103d])?([\u1032\u1036\u1039\u102d\u102e\u108b\u108c\u108d\u108e]?)(\u102f)?\u1037",
                "$1$2$3$4\u1095");
        rule.put("([\u102f\u1014\u1030\u103d])([\u1032\u1036\u1039\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1037",
                "$1$2\u1094");
        rule.put("([\u103b])([\u1000-\u1021])([\u1087]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u102f",
                "$1$2$3$4\u1033");
        rule.put("([\u103b])([\u1000-\u1021])([\u1087]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1030",
                "$1$2$3$4\u1034");
        rule.put("([\u103a\u103c\u100a\u1020\u1025])([\u103d]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u102f",
                "$1$2$3\u1033");
        rule.put("([\u103a\u103c\u100a\u101b])(\u103d?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1030",
                "$1$2$3\u1034");
        rule.put("\u100a\u103d", "\u100a\u1087");
        rule.put("\u103d\u1030", "\u1089");
        rule.put("\u103b([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])",
                "\u107e$1");
        rule.put("\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])",
                "\u1084$1$2$3");
        rule.put("\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])",
                "\u1082$1$2");
        rule.put("\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])",
                "\u1080$1$2$3");
        rule.put("\u103b([\u1000-\u1021])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])",
                "\u1083$1$2$3");
        rule.put("\u103b([\u1000-\u1021])([\u103c\u108a])", "\u1081$1$2");
        rule.put("\u103b([\u1000-\u1021])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])",
                "\u107f$1$2$3");
        rule.put("\u103a\u103d", "\u103d\u103a");
        rule.put("\u103a([\u103c\u108a])", "$1\u107d");
        rule.put("([\u1033\u1034])\u1094", "$1\u1095");
        rule.put("\u108F\u1071", "\u108F\u1072");
        rule.put("([\u1000-\u1021])([\u107B\u1066])\u102C", "$1\u102C$2");
        rule.put("\u102C([\u107B\u1066])\u1037", "\u102C$1\u1094");

        return replace_with_rule(rule, input);
    }

    public static String zg2uni(String input) {

        Map<String, String> rule = new LinkedHashMap<>();
        rule.put("([\u102D\u102E\u103D\u102F\u1037\u1095])\\1+", "$1");
        rule.put("\u200B", "");
        rule.put("\u103d\u103c", "\u108a");
        rule.put("(\u103d|\u1087)", "\u103e");
        rule.put("\u103c", "\u103d");
        rule.put("(\u103b|\u107e|\u107f|\u1080|\u1081|\u1082|\u1083|\u1084)", "\u103c");
        rule.put("(\u103a|\u107d)", "\u103b");
        rule.put("\u1039", "\u103a");
        rule.put("(\u1066|\u1067)", "\u1039\u1006");
        rule.put("\u106a", "\u1009");
        rule.put("\u106b", "\u100a");
        rule.put("\u106c", "\u1039\u100b");
        rule.put("\u106d", "\u1039\u100c");
        rule.put("\u106e", "\u100d\u1039\u100d");
        rule.put("\u106f", "\u100d\u1039\u100e");
        rule.put("\u1070", "\u1039\u100f");
        rule.put("(\u1071|\u1072)", "\u1039\u1010");
        rule.put("\u1060", "\u1039\u1000");
        rule.put("\u1061", "\u1039\u1001");
        rule.put("\u1062", "\u1039\u1002");
        rule.put("\u1063", "\u1039\u1003");
        rule.put("\u1065", "\u1039\u1005");
        rule.put("\u1068", "\u1039\u1007");
        rule.put("\u1069", "\u1039\u1008");
        rule.put("(\u1073|\u1074)", "\u1039\u1011");
        rule.put("\u1075", "\u1039\u1012");
        rule.put("\u1076", "\u1039\u1013");
        rule.put("\u1077", "\u1039\u1014");
        rule.put("\u1078", "\u1039\u1015");
        rule.put("\u1079", "\u1039\u1016");
        rule.put("\u107a", "\u1039\u1017");
        rule.put("\u107c", "\u1039\u1019");
        rule.put("\u1085", "\u1039\u101c");
        rule.put("\u1033", "\u102f");
        rule.put("\u1034", "\u1030");
        rule.put("\u103f", "\u1030");
        rule.put("\u1086", "\u103f");
        rule.put("\u1036\u1088", "\u1088\u1036");
        rule.put("\u1088", "\u103e\u102f");
        rule.put("\u1089", "\u103e\u1030");
        rule.put("\u108a", "\u103d\u103e");
        rule.put("\u103B\u1064", "\u1064\u103B");
        rule.put("(\u1031)?([\u1000-\u1021\u1040-\u1049])\u1064", "\u1004\u103a\u1039$1$2");
        rule.put("(\u1031)?([\u1000-\u1021])(\u103b)?\u108b", "\u1004\u103a\u1039$1$2$3\u102d");
        rule.put("(\u1031)?([\u1000-\u1021])(\u103b)?\u108c", "\u1004\u103a\u1039$1$2$3\u102e");
        rule.put("(\u1031)?([\u1000-\u1021])\u108d", "\u1004\u103a\u1039$1$2\u1036");
        rule.put("\u108e", "\u102d\u1036");
        rule.put("\u108f", "\u1014");
        rule.put("\u1090", "\u101b");
        rule.put("\u1091", "\u100f\u1039\u100d");
        rule.put("\u1092", "\u100b\u1039\u100c");
        rule.put("\u1019\u102c(\u107b|\u1093)", "\u1019\u1039\u1018\u102c");
        rule.put("(\u107b|\u1093)", "\u1039\u1018");
        rule.put("(\u1094|\u1095)", "\u1037");
        rule.put("([\u1000-\u1021])\u1037\u1032", "$1\u1032\u1037");
        rule.put("\u1096", "\u1039\u1010\u103d");
        rule.put("\u1097", "\u100b\u1039\u100b");
        rule.put("\u103c([\u1000-\u1021])([\u1000-\u1021])?", "$1\u103c$2");
        rule.put("([\u1000-\u1021])\u103c\u103a", "\u103c$1\u103a");
        rule.put("\u1047(?=[\u102c-\u1030\u1032\u1036-\u1038\u103d\u1038])", "\u101b");
        rule.put("\u1031\u1047", "\u1031\u101b");
        rule.put("\u1040(\u102e|\u102f|\u102d\u102f|\u1030|\u1036|\u103d|\u103e)", "\u101d$1");
        rule.put("([^\u1040\u1041\u1042\u1043\u1044\u1045\u1046\u1047\u1048\u1049])\u1040\u102b",
                "$1\u101d\u102b");
        rule.put("([\u1040\u1041\u1042\u1043\u1044\u1045\u1046\u1047\u1048\u1049])\u1040\u102b(?!\u1038)",
                "$1\u101d\u102b");
        rule.put("^\u1040(?=\u102b)", "\u101d");
        rule.put("\u1040\u102d(?!\u0020?/)", "\u101d\u102d");
        rule.put("([^\u1040-\u1049])\u1040([^\u1040-\u1049\u0020]|[\u104a\u104b])", "$1\u101d$2");
        rule.put("([^\u1040-\u1049])\u1040(?=[\\f\\n\\r])", "$1\u101d");
        rule.put("([^\u1040-\u1049])\u1040$", "$1\u101d");
        rule.put("\u1031([\u1000-\u1021\u103f])(\u103e)?(\u103b)?", "$1$2$3\u1031");
        rule.put("([\u1000-\u1021])\u1031([\u103b\u103c\u103d\u103e]+)", "$1$2\u1031");
        rule.put("\u1032\u103d", "\u103d\u1032");
        rule.put("([\u102d\u102e])\u103b", "\u103b$1");
        rule.put("\u103d\u103b", "\u103b\u103d");
        rule.put("\u103a\u1037", "\u1037\u103a");
        rule.put("\u102f(\u102d|\u102e|\u1036|\u1037)\u102f", "\u102f$1");
        rule.put("(\u102f|\u1030)(\u102d|\u102e)", "$2$1");
        rule.put("(\u103e)(\u103b|\u103c)", "$2$1");
        rule.put("\u1025(?=[\u1037]?[\u103a\u102c])", "\u1009");
        rule.put("\u1025\u102e", "\u1026");
        rule.put("\u1005\u103b", "\u1008");
        rule.put("\u1036(\u102f|\u1030)", "$1\u1036");
        rule.put("\u1031\u1037\u103e", "\u103e\u1031\u1037");
        rule.put("\u1031\u103e\u102c", "\u103e\u1031\u102c");
        rule.put("\u105a", "\u102b\u103a");
        rule.put("\u1031\u103b\u103e", "\u103b\u103e\u1031");
        rule.put("(\u102d|\u102e)(\u103d|\u103e)", "$2$1");
        rule.put("\u102c\u1039([\u1000-\u1021])", "\u1039$1\u102c");
        rule.put("\u1039\u103c\u103a\u1039([\u1000-\u1021])", "\u103a\u1039$1\u103c");
        rule.put("\u103c\u1039([\u1000-\u1021])", "\u1039$1\u103c");
        rule.put("\u1036\u1039([\u1000-\u1021])", "\u1039$1\u1036");
        rule.put("\u104e", "\u104e\u1004\u103a\u1038");
        rule.put("\u1040(\u102b|\u102c|\u1036)", "\u101d$1");
        rule.put("\u1025\u1039", "\u1009\u1039");
        rule.put("([\u1000-\u1021])\u103c\u1031\u103d", "$1\u103c\u103d\u1031");
        rule.put("([\u1000-\u1021])\u103b\u1031\u103d(\u103e)?", "$1\u103b\u103d$2\u1031");
        rule.put("([\u1000-\u1021])\u103d\u1031\u103b", "$1\u103b\u103d\u1031");
        rule.put("([\u1000-\u1021])\u1031(\u1039[\u1000-\u1021])", "$1$2\u1031");
        rule.put("\u1038\u103a", "\u103a\u1038");
        rule.put("\u102d\u103a|\u103a\u102d", "\u102d");
        rule.put("\u102d\u102f\u103a", "\u102d\u102f");
        rule.put("\u0020\u1037", "\u1037");
        rule.put("\u1037\u1036", "\u1036\u1037");
        rule.put("[\u102d]+", "\u102d");
        rule.put("[\u103a]+", "\u103a");
        rule.put("[\u103d]+", "\u103d");
        rule.put("[\u1037]+", "\u1037");
        rule.put("[\u102e]+", "\u102e");
        rule.put("\u102d\u102e|\u102e\u102d", "\u102e");
        rule.put("\u102f\u102d", "\u102d\u102f");
        rule.put("\u1037\u1037", "\u1037");
        rule.put("\u1032\u1032", "\u1032");
        rule.put("\u1044\u1004\u103a\u1038", "\u104E\u1004\u103a\u1038");
        rule.put("([\u102d\u102e])\u1039([\u1000-\u1021])", "\u1039$2$1");
        rule.put("(\u103c\u1031)\u1039([\u1000-\u1021])", "\u1039$2$1");
        rule.put("\u1036\u103d", "\u103d\u1036");

        return replace_with_rule(rule, input);

    }

    private static String replace_with_rule(Map<String, String> rule, String input) {
        // because of JDK 7 bugs in Android
        String output = input.replace("null", "\uFFFF\uFFFF");

        for (Map.Entry<String, String> entry : rule.entrySet()) {
            output = output.replaceAll(entry.getKey(),entry.getValue());
            output = output.replace("null", "");
        }
        output = output.replace("\uFFFF\uFFFF", "null");
        return output;
    }

}