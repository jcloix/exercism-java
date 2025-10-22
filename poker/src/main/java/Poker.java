import java.util.*;

public class Poker {

    private List<String> hands;

    public Poker(List<String> hands) {
        this.hands = hands;
    }

    public List<String> getBestHands() {
        List<PokerHand> evaluatedHands = new ArrayList<>();

        for (String hand : hands) {
            PokerHand pokerHand = new PokerHand(hand);
            evaluatedHands.add(pokerHand);
        }

        // Sort hands so best is last (natural order ascending)
        Collections.sort(evaluatedHands);

        // Find the best rank (last element)
        PokerHand bestHand = evaluatedHands.get(evaluatedHands.size() - 1);

        List<String> result = new ArrayList<>();
        for (PokerHand ph : evaluatedHands) {
            if (ph.compareTo(bestHand) == 0) {
                result.add(ph.originalHand);
            }
        }
        return result;
    }

    private static class PokerHand implements Comparable<PokerHand> {
        private String originalHand;
        private List<Card> cards;
        private HandRank rank;
        private List<Integer> rankValues;

        private Map<Integer, Integer> valueCounts;
        private boolean isFlush;
        private boolean isStraight;
        private boolean isAceLowStraight = false;  // <-- ajouté

        public PokerHand(String hand) {
            this.originalHand = hand;
            this.cards = parseCards(hand);
            evaluate();
        }

        private List<Card> parseCards(String hand) {
            List<Card> cards = new ArrayList<>();
            for (String c : hand.split(" ")) {
                cards.add(new Card(c));
            }
            cards.sort(Collections.reverseOrder());
            return cards;
        }

        private void evaluate() {
            valueCounts = new HashMap<>();
            for (Card card : cards) {
                valueCounts.put(card.value, valueCounts.getOrDefault(card.value, 0) + 1);
            }

            char suit = cards.get(0).suit;
            isFlush = cards.stream().allMatch(c -> c.suit == suit);

            isStraight = true;
            for (int i = 1; i < cards.size(); i++) {
                if (cards.get(i - 1).value - cards.get(i).value != 1) {
                    isStraight = false;
                    break;
                }
            }

            if (!isStraight) {
                isStraight = checkAceLowStraight();
            }

            determineRankAndValues();
        }

        private boolean checkAceLowStraight() {
            int[] aceLow = {14, 5, 4, 3, 2};
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).value != aceLow[i]) return false;
            }
            // Traiter l'As comme 1, donc tri ascendant
            cards.sort((a, b) -> Integer.compare(
                    a.value == 14 ? 1 : a.value,
                    b.value == 14 ? 1 : b.value));
            isAceLowStraight = true;  // <-- signaler ace-low
            return true;
        }

        private void determineRankAndValues() {
            List<Map.Entry<Integer, Integer>> counts = new ArrayList<>(valueCounts.entrySet());
            counts.sort((e1, e2) -> {
                int freqComp = Integer.compare(e2.getValue(), e1.getValue());
                if (freqComp != 0) return freqComp;
                return Integer.compare(e2.getKey(), e1.getKey());
            });

            boolean four = counts.size() > 0 && counts.get(0).getValue() == 4;
            boolean three = counts.size() > 0 && counts.get(0).getValue() == 3;
            int pairs = 0;
            for (Map.Entry<Integer, Integer> e : counts) {
                if (e.getValue() == 2) pairs++;
            }

            if (isFlush && isStraight) {
                if (!isAceLowStraight && cards.get(0).value == 14) {
                    rank = HandRank.ROYAL_FLUSH;
                    rankValues = List.of();
                    return;
                }
                rank = HandRank.STRAIGHT_FLUSH;
                // La valeur haute est 5 si ace-low, sinon la plus haute carte
                rankValues = List.of(isAceLowStraight ? 5 : cards.get(0).value);
                return;
            }
            if (four) {
                rank = HandRank.FOUR_OF_A_KIND;
                int fourVal = counts.get(0).getKey();
                int kicker = counts.size() > 1 ? counts.get(1).getKey() : 0;
                rankValues = List.of(fourVal, kicker);
                return;
            }
            if (three && pairs >= 1) {
                rank = HandRank.FULL_HOUSE;
                int threeVal = counts.get(0).getKey();
                int pairVal = counts.get(1).getKey();
                rankValues = List.of(threeVal, pairVal);
                return;
            }
            if (isFlush) {
                rank = HandRank.FLUSH;
                rankValues = getSortedCardValues();
                return;
            }
            if (isStraight) {
                rank = HandRank.STRAIGHT;
                rankValues = List.of(isAceLowStraight ? 5 : cards.get(0).value);
                return;
            }
            if (three) {
                rank = HandRank.THREE_OF_A_KIND;
                int threeVal = counts.get(0).getKey();
                List<Integer> kickers = new ArrayList<>();
                for (Map.Entry<Integer, Integer> e : counts) {
                    if (e.getValue() == 1) kickers.add(e.getKey());
                }
                kickers.sort(Comparator.reverseOrder());
                rankValues = new ArrayList<>();
                rankValues.add(threeVal);
                rankValues.addAll(kickers);
                return;
            }
            if (pairs == 2) {
                rank = HandRank.TWO_PAIR;
                List<Integer> pairVals = new ArrayList<>();
                List<Integer> kickers = new ArrayList<>();
                for (Map.Entry<Integer, Integer> e : counts) {
                    if (e.getValue() == 2) pairVals.add(e.getKey());
                    else kickers.add(e.getKey());
                }
                pairVals.sort(Comparator.reverseOrder());
                kickers.sort(Comparator.reverseOrder());
                rankValues = new ArrayList<>();
                rankValues.addAll(pairVals);
                rankValues.addAll(kickers);
                return;
            }
            if (pairs == 1) {
                rank = HandRank.ONE_PAIR;
                int pairVal = 0;
                List<Integer> kickers = new ArrayList<>();
                for (Map.Entry<Integer, Integer> e : counts) {
                    if (e.getValue() == 2) pairVal = e.getKey();
                    else kickers.add(e.getKey());
                }
                kickers.sort(Comparator.reverseOrder());
                rankValues = new ArrayList<>();
                rankValues.add(pairVal);
                rankValues.addAll(kickers);
                return;
            }
            // High card
            rank = HandRank.HIGH_CARD;
            rankValues = getSortedCardValues();
        }

        private List<Integer> getSortedCardValues() {
            List<Integer> vals = new ArrayList<>();
            for (Card c : cards) {
                vals.add(c.value);
            }
            return vals;
        }

        @Override
        public int compareTo(PokerHand o) {
            if (this.rank != o.rank) {
                return Integer.compare(this.rank.ordinal(), o.rank.ordinal());
            }
            for (int i = 0; i < Math.min(this.rankValues.size(), o.rankValues.size()); i++) {
                int cmp = Integer.compare(this.rankValues.get(i), o.rankValues.get(i));
                if (cmp != 0) return cmp;
            }
            return 0;
        }
    }


    private static class Card implements Comparable<Card> {
        int value;
        char suit;

        public Card(String card) {
            String valStr = card.length() == 3 ? card.substring(0, 2) : card.substring(0, 1);
            this.value = cardValue(valStr);
            this.suit = card.charAt(card.length() - 1);
        }

        private int cardValue(String val) {
            switch (val) {
                case "2": return 2;
                case "3": return 3;
                case "4": return 4;
                case "5": return 5;
                case "6": return 6;
                case "7": return 7;
                case "8": return 8;
                case "9": return 9;
                case "10": case "T": return 10;
                case "J": return 11;
                case "Q": return 12;
                case "K": return 13;
                case "A": return 14;
                default: throw new IllegalArgumentException("Invalid card value: " + val);
            }
        }

        @Override
        public int compareTo(Card o) {
            return Integer.compare(this.value, o.value);
        }
    }

    private enum HandRank {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }
}
