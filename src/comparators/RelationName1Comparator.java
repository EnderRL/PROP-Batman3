package comparators;

import domain.Relation;

import java.util.Comparator;

public class RelationName1Comparator implements Comparator<Relation> {

    @Override
    public int compare(Relation o1, Relation o2) {
        int comp = o1.getNode1().compareTo(o2.getNode1());
        return (comp == 0 ? o1.getNode2().compareTo(o2.getNode2()) : comp);
    }
}
