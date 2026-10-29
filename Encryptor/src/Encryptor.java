public class Encryptor {
    static void main(String[] args) {
        // TODO: actual command line interface
        String genusCryptographiae, introitus, exitus, modus, clavis;

        try {
            genusCryptographiae =  args[0].toLowerCase();
            introitus = args[1];

            EncryptionAlgorithm encryptor = switch(genusCryptographiae){
                case "caesar" -> {
                    if (args.length > 4) {
                        exitus = args[2];
                        modus = args[3];
                        clavis = args[4];
                    } else {
                        modus = args[2];
                        clavis = args[3];
                    }
                    if (!clavis.chars().allMatch(Character::isDigit)){
                        throw new IllegalArgumentException("CAESAR KEY MUST BE INTEGER NUMBER");
                    }
                    yield createCaesar(modus, clavis);
                }
                case "vigenere" -> {
                    if (args.length > 4) {
                        exitus = args[2];
                        modus = args[3];
                        clavis = args[4];
                    } else {
                        modus = args[2];
                        clavis = args[3];
                    }
                    yield createVigenere(modus, clavis);
                }
                case "sha256" -> {
                    exitus = args.length > 2 ? args[2] : null;
                    yield new Sha256();
                }
                default -> throw new MatchException("ENCRYPTION TYPE MATCH FAILURE", null);
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("NOT ENOUGH ARGUMENTS PROVIDED");
        } catch (Throwable _){}
    }

    private static Caesar createCaesar(String modus, String clavis) {
        Caesar o = new Caesar();
        o.setDecrypt(parseModus(modus));
        o.setKey(Integer.parseInt(clavis));
        return o;
    }

    private static Vigenere createVigenere(String modus, String clavis) {
        Vigenere o = new Vigenere();
        o.setDecrypt(parseModus(modus));
        o.setKey(clavis);
        return o;
    }

    private static boolean parseModus(String modus){
        if (modus.contentEquals("decrypt") || modus.contentEquals("de") || modus.contentEquals("d")){
            return true;
        } else if (modus.contentEquals("encrypt") || modus.contentEquals("en") || modus.contentEquals("e")) {
            return false;
        } else {
            throw new IllegalArgumentException("ENCRYPTION DIRECTION NOT VALID");
        }
    }
}