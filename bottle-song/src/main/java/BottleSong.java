class BottleSong {

    String recite(int startBottles, int takeDown) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < takeDown; i++) {
            int nbBottle = startBottles-i;
            if(nbBottle==1) {
                sb.append(getN(nbBottle)).append(" green bottle hanging on the wall,\n");
                sb.append(getN(nbBottle)).append(" green bottle hanging on the wall,\n");
            } else {
                sb.append(getN(nbBottle)).append(" green bottles hanging on the wall,\n");
                sb.append(getN(nbBottle)).append(" green bottles hanging on the wall,\n");
            }
            sb.append("And if one green bottle should accidentally fall,\n");
            if(nbBottle==2) {
                sb.append("There'll be ").append(getN(nbBottle-1,false)).append(" green bottle hanging on the wall.\n");
            } else {
                sb.append("There'll be ").append(getN(nbBottle-1,false)).append(" green bottles hanging on the wall.\n");
            }

            if(i < (takeDown-1)) sb.append("\n");
        }
        return sb.toString();
    }

    String getN(int n) {
        return getN(n,true);
    }

    String getN(int n, boolean firstWord) {
        if (firstWord) return getEnglishNumber(n);
        return getEnglishNumber(n).toLowerCase();
    }

    String getEnglishNumber(int n) {
        return switch (n) {
            case 10 -> "Ten";
            case 9 -> "Nine";
            case 8 -> "Eight";
            case 7 -> "Seven";
            case 6 -> "Six";
            case 5 -> "Five";
            case 4 -> "Four";
            case 3 -> "Three";
            case 2 -> "Two";
            case 1 -> "One";
            case 0 -> "No";
            default -> "Error";
        };
    }

}