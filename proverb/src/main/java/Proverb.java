class Proverb {
    String s = "";
    Proverb(String[] words) {
        if(words.length==0) return;
        StringBuilder sb = new StringBuilder();
        for(int i= 1; i < words.length; i++) {
            sb.append(String.format("For want of a %s the %s was lost.\n",words[i-1],words[i]));
        }
        sb.append(String.format("And all for the want of a %s.",words[0]));
        s = sb.toString();
    }

    String recite() {
        return s;
    }

}
