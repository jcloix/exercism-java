import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ProteinTranslator {
    private static final Map<String, String> CODON_MAP = Map.ofEntries(
            Map.entry("AUG", "Methionine"),
            Map.entry("UUU", "Phenylalanine"),
            Map.entry("UUC", "Phenylalanine"),
            Map.entry("UUA", "Leucine"),
            Map.entry("UUG", "Leucine"),
            Map.entry("UCU", "Serine"),
            Map.entry("UCC", "Serine"),
            Map.entry("UCA", "Serine"),
            Map.entry("UCG", "Serine"),
            Map.entry("UAU", "Tyrosine"),
            Map.entry("UAC", "Tyrosine"),
            Map.entry("UGU", "Cysteine"),
            Map.entry("UGC", "Cysteine"),
            Map.entry("UGG", "Tryptophan"),
            Map.entry("UAA", "STOP"),
            Map.entry("UAG", "STOP"),
            Map.entry("UGA", "STOP")
    );
    List<String> translate(String rnaSequence) {
        List<String> result = new ArrayList<>();
        for(int i= 0; i < rnaSequence.length(); i+=3) {
            String seq = rnaSequence.substring(i,Math.min(i+3,rnaSequence.length()));
            String codon = CODON_MAP.get(seq);
            if(codon== null) {
                throw new IllegalArgumentException("Invalid codon");
            }
            if(codon.equals("STOP")) {
                return result;
            }
            result.add(codon);
        }
        return result;
    }
}
