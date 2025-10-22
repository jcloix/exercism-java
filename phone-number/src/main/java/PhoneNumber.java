class PhoneNumber {
    String phoneNumber;
    PhoneNumber(String numberString) {
        String clean = cleanPhoneNumber(numberString);
        validate(numberString, clean);
        phoneNumber=clean;
    }

    String getNumber() {
        return phoneNumber;
    }

    void validate(String uncleanPhoneNumber, String cleanPhoneNumber) {
        if(uncleanPhoneNumber.length() < 10) {
            throw new IllegalArgumentException("must not be fewer than 10 digits");
        }
        if(cleanPhoneNumber.length() > 10) {
            if(cleanPhoneNumber.length() == 11 && !uncleanPhoneNumber.startsWith("+1") && !uncleanPhoneNumber.startsWith("1")) {
                throw new IllegalArgumentException("11 digits must start with 1");
            } else {
                throw new IllegalArgumentException("must not be greater than 11 digits");
            }
        }
        if(cleanPhoneNumber.charAt(0) == '0') {
            throw new IllegalArgumentException("area code cannot start with zero");
        }
        if(cleanPhoneNumber.charAt(0) == '1' ) {
            throw new IllegalArgumentException("area code cannot start with one");
        }
        if(cleanPhoneNumber.charAt(3) == '0') {
            throw new IllegalArgumentException("exchange code cannot start with zero");
        }
        if(cleanPhoneNumber.charAt(3) == '1') {
            throw new IllegalArgumentException("exchange code cannot start with one");
        }
        if(cleanPhoneNumber.chars().filter(Character::isLetter).findAny().isPresent()) {
            throw new IllegalArgumentException("letters not permitted");
        } else if(cleanPhoneNumber.chars().filter(c->!Character.isDigit(c)).findAny().isPresent()) {
            throw new IllegalArgumentException("punctuations not permitted");
        }
    }

    String cleanPhoneNumber(String numberString) {
        String clean = numberString.replaceAll("[ .-]","");
        if(clean.startsWith("+1")) {
            clean=clean.substring(2);
        }
        if(clean.startsWith("1")) {
            clean=clean.substring(1);
        }
        if(clean.charAt(0) == '(' && clean.charAt(4) == ')') {
            clean = clean.substring(1,4) + clean.substring(5);
        }
        return clean;
    }

}