import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ledger {
    public LedgerEntry createLedgerEntry(String d, String desc, int c) {
        LedgerEntry le = new LedgerEntry();
        le.setChange(c);
        le.setDescription(desc);
        le.setLocalDate(LocalDate.parse(d));
        return le;
    }

    public String format(String cur, String loc, LedgerEntry[] entries) {
        validate(cur, loc);
        Currency currency = Currency.findByName(cur);
        LocaleEnum locale = LocaleEnum.findByName(loc);

        StringBuilder sb = new StringBuilder(locale.getHeader());
        if(entries.length ==0) return sb.toString();
        List<LedgerEntry> sortedEntries = Arrays.stream(entries).sorted(LedgerEntry::compareTo).toList();
        for (LedgerEntry e : sortedEntries) {
            sb.append("\n").append(String.format("%s | %-25s | %13s",
                    e.getLocalDate().format(DateTimeFormatter.ofPattern(locale.getDateFormat())),
                    e.getTruncateDescription(),
                    locale.formatChange(e.getChange(),currency.getCurr())));
        }
        return sb.toString();
    }

    public static String truncate(String s, int toTruncate) {
        if(s.length() > toTruncate) {
            return s.substring(0,toTruncate-3) + "...";
        }
        return s;
    }

    public static class LedgerEntry implements Comparable<LedgerEntry> {
        LocalDate localDate;
        String description;
        double change;

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public String getDescription() {
            return description;
        }

        public String getTruncateDescription() {
            return Ledger.truncate(description,25);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        @Override
        public int compareTo(LedgerEntry o) {
            Boolean b1 = change < 0;
            Boolean b2 = o.change < 0;
            int compare = b2.compareTo(b1);
            return compare!=0? compare:localDate.compareTo(o.localDate);
        }
    }

    void validate(String cur, String loc) {
        if (!cur.equals("USD") && !cur.equals("EUR")) {
            throw new IllegalArgumentException("Invalid currency");
        }
        if (!loc.equals("en-US") && !loc.equals("nl-NL")) {
            throw new IllegalArgumentException("Invalid locale");
        }
    }

}

enum Currency {
    EUR("EUR","€"),
    USD("USD","$");
    private String name;
    private String curr;

    Currency(String name, String curr) {
        this.name = name;
        this.curr = curr;
    }

    public String getName() {
        return name;
    }

    public String getCurr() {
        return curr;
    }


    public static Currency findByName(String name) {
        return Arrays.stream(Currency.values())
                .filter(l->l.getName().equals(name))
                .findFirst().orElse(null);
    }
}

enum LocaleEnum {
    EN_US("en-US",Locale.ENGLISH,"MM/dd/yyyy","Date       | Description               | Change       "),
    NL_NL("nl-NL",Locale.forLanguageTag("nl-NL"),"dd/MM/yyyy","Datum      | Omschrijving              | Verandering  ") {
        @Override
        public String formatChange(double change, String curr) {
            String amount = formatNumber(change);
            if(change < 0) {
                return curr + " -" + amount + " ";
            }
            return " " + curr+ " " +amount + " ";
        }
    };
    private String name;
    private Locale locale;
    private String dateFormat;
    private String header;

    LocaleEnum(String name, Locale locale,  String dateFormat,String header) {
        this.name = name;
        this.locale = locale;
        this.dateFormat = dateFormat;
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getHeader() {
        return header;
    }

    public String formatNumber(double change) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(getLocale());
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(Math.abs(change/100));
    }

    public String formatChange(double change, String curr) {
        String amount = formatNumber(change);
        if(change < 0) return "("+curr+amount+")";
        return curr + amount + " ";
    }


    public static LocaleEnum findByName(String name) {
        return Arrays.stream(LocaleEnum.values())
                .filter(l->l.getName().equals(name))
                .findFirst().orElse(null);
    }
}
