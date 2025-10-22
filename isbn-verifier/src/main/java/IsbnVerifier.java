class IsbnVerifier {

    boolean isValid(String stringToVerify) {
        if(stringToVerify.isBlank()) return false;
        if(!stringToVerify.matches("[0-9\\-]*[0-9X]?")) {
            return false;
        }
        String cleaned = stringToVerify.replaceAll("-","");
        if(cleaned.length() != 10) return false;
        int result =0;
        for(int i=0; i < cleaned.length(); i++) {
            char c = cleaned.charAt(i);
            int val = c != 'X' ? c - '0' :10;
            result+=val*(10-i);
        }
        return result % 11 == 0;
    }

}
