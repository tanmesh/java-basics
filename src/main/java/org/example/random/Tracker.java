package org.example.random;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Server names consist of an alphabetic host type (e.g. "apibox") concatenated with the server number, with server
numbers allocated as before (so "apibox1", "apibox2", etc. are valid hostnames).

Write a name tracking class with two operations, allocate(host_type) and deallocate(hostname). The former should
reserve and return the next available hostname, while the latter should release that hostname back into the pool.

# For example:

# >> tracker = Tracker()
# >> tracker.allocate("apibox")
# "apibox1"
# >> tracker.allocate("apibox")
# "apibox2"
# >> tracker.deallocate("apibox1")
# nil
# >> tracker.allocate("apibox")
# "apibox1"
# >> tracker.allocate("sitebox")
# "sitebox1
# >> tracker.allocate("#$@%")
 */
public class Tracker {
    private static Map<String, Set<Integer>> serversMap;

    public Tracker() {
        serversMap = new HashMap<>();
    }

    private static int next_server_number(Set<Integer> servers) {
        int num = 1;
        while (servers.contains(num)) {
            ++num;
        }
        return num;
    }

    public void allocate(String hostType) {
        Set<Integer> servers = serversMap.get(hostType);
        if (servers == null) {
            servers = new HashSet<>();
        }
        int num = next_server_number(servers);

        System.out.println("Allocating -- " + hostType + " --> " + (hostType + num));
        servers.add(num);
        serversMap.put(hostType, servers);
    }

    public void deallocate(String hostName) {
        int i = 0;
        while (i < hostName.length() && 'a' <= hostName.charAt(i) && hostName.charAt(i) <= 'z') {
            ++i;
        }
        String hostType = hostName.substring(0, i);
        int num = Integer.parseInt(hostName.substring(i));

        System.out.println("Deallocating -- " + hostName);
        Set<Integer> servers = serversMap.get(hostType);
        servers.remove(num);
        serversMap.put(hostType, servers);
    }

    public static void main(String[] args) {
        Tracker tracker = new Tracker();

        tracker.allocate("apibox");
        tracker.allocate("apibox");
        tracker.deallocate("apibox1");
        tracker.allocate("apibox");
        tracker.allocate("sitebox");
        tracker.allocate("#$@%");
    }
}
