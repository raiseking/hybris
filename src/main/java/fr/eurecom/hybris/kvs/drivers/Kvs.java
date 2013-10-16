package fr.eurecom.hybris.kvs.drivers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Kvs {

    protected final String id;
    protected transient boolean enabled;
    protected transient String rootContainer;

    /* measures to compare the providers */
    protected transient long writeLatency;
    protected transient long readLatency;
    protected transient int cost; // $ cents per GB

    /**
     * Static Comparator objects for ordering the Kvs list
     * according to both read and write latencies.
     */
    public transient static final Comparator<Kvs> COMPARATOR_BY_READS = new Comparator<Kvs>() {
        public int compare(Kvs kvs1, Kvs kvs2) {
            if (kvs1.getReadLatency() < kvs2.getReadLatency() || !kvs2.isEnabled())
                return -1;
            else if (kvs1.getReadLatency() > kvs2.getReadLatency() || !kvs1.isEnabled())
                return 1;
            else
                return 0;
        }
    };
    public transient static final Comparator<Kvs> COMPARATOR_BY_WRITES = new Comparator<Kvs>() {
        public int compare(Kvs kvs1, Kvs kvs2) {
            if (kvs1.getWriteLatency() < kvs2.getWriteLatency() || !kvs2.isEnabled())
                return -1;
            else if (kvs1.getWriteLatency() > kvs2.getWriteLatency() || !kvs1.isEnabled())
                return 1;
            else
                return 0;
        }
    };

    public Kvs(String id, String container, boolean enabled, int cost) {
        this.id = id;
        this.enabled = enabled;
        this.cost = cost;
        this.rootContainer = container;

        this.readLatency = 0;
        this.writeLatency = 0;
    }

    public String getId()           { return this.id; }
    public boolean isEnabled()      { return this.enabled; }
    public void setEnabled(boolean enabled)         { this.enabled = enabled; }
    public long getWriteLatency()   { return this.writeLatency; }
    public void setWriteLatency(long writeLatency) { this.writeLatency = writeLatency; }
    public long getReadLatency()    { return this.readLatency; }
    public void setReadLatency(long readLatency)    { this.readLatency = readLatency; }
    public int getCost()            { return this.cost; }
    public void setCost(int cost)   { this.cost = cost; }

    /* APIs */
    public void put(String key, byte[] value) throws IOException { }
    public byte[] get(String key) throws IOException { return null; }
    public List<String> list() throws IOException { return null; }
    public void delete(String key) throws IOException { }
    public void shutdown() throws IOException { }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Kvs other = (Kvs) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        } else if (!this.id.equals(other.id))
            return false;
        return true;
    }

    public String toString() {
        return this.id;
    }

    public String toVerboseString() {
        return "Kvs (" + this.id + ") [enabled=" + this.enabled +
                ", writeLatency=" + this.writeLatency +
                ", readLatency=" + this.readLatency +
                ", cost=" + this.cost + "]";
    }
}
