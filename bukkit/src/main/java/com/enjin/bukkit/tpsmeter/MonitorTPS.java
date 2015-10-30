/**
 *
 */
package com.enjin.bukkit.tpsmeter;

import java.util.LinkedList;

import com.enjin.bukkit.EnjinMinecraftPlugin;

/**
 * @author joshua
 */
public class MonitorTPS implements Runnable {

    private long lasttime = System.currentTimeMillis();
    private LinkedList<Double> tpslist = new LinkedList<Double>();
    private int tickmeasurementinterval = 40;
    EnjinMinecraftPlugin plugin;
    private int maxentries = 25;

    public MonitorTPS(EnjinMinecraftPlugin plugin) {
        this.plugin = plugin;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public synchronized void run() {
        long currenttime = System.currentTimeMillis();
        double timespent = ((double) currenttime - (double) lasttime) / 1000f;
        double tps = (double) tickmeasurementinterval / timespent;
        if (tpslist.size() >= maxentries) {
            tpslist.pop();
        }
        tpslist.add(tps);
        //plugin.debug("Current TPS: " + tps);
        lasttime = currenttime;
    }

    public synchronized double getTPSAverage() {
        if (tpslist.size() > 0) {
            double alltps = 0;
            for (Double tps : tpslist) {
                alltps += tps.floatValue();
            }
            return alltps / (double) tpslist.size();
        }
        return -1;
    }

    public synchronized double getLastTPSMeasurement() {
        if (tpslist.size() > 0) {
            return tpslist.getLast();
        }
        return -1;
    }

    public synchronized void clearTPS() {
        double lastmeasurement = getLastTPSMeasurement();
        tpslist.clear();
        tpslist.add(lastmeasurement);
    }
}