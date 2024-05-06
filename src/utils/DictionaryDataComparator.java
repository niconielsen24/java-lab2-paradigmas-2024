package utils;

import java.util.Comparator;

public class DictionaryDataComparator implements Comparator<DictionaryData> {

    @Override
    public int compare(DictionaryData arg0, DictionaryData arg1) {
        return arg0.getLabel().compareTo(arg1.getLabel());
    }
    
}
