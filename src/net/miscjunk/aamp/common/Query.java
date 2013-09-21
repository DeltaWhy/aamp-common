package net.miscjunk.aamp.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Query implements Iterable<SimpleQuery> {
    List<SimpleQuery> parts;
    
    public Query(SimpleQuery... parts) {
        this.parts = Arrays.asList(parts);
    }
    public Query() {
        this.parts = new ArrayList<SimpleQuery>();
    }
    
    public void addPart(SimpleQuery part) {
        parts.add(part);
    }

    public Iterator<SimpleQuery> iterator() {
        return parts.iterator();
    }
}
