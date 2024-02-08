package org.example.random;

import java.util.*;

/*
# You're running a pool of servers where the servers are numbered sequentially starting from 1.
Over time, any given server might explode, in which case its server number is made available for reuse.
When a new server is launched, it should be given the lowest available number.

# Write a function which, given the list of currently allocated server numbers, returns the number of the next server to allocate.
In addition, you should demonstrate your approach to testing that your function is correct.
You may choose to use an existing testing library for your language if you choose, or you may write your own process if you prefer.

# For example, your function should behave something like the following:

#   >> next_server_number([5, 3, 1])
#   2
#   >> next_server_number([5, 4, 1, 2])
#   3
#   >> next_server_number([3, 2, 1])
#   4
#   >> next_server_number([2, 3])
#   1
#   >> next_server_number([])
#   1
#   >> next_server_number([1,1.5,2,2.5,3,3.5,4,5,5.5])
#   6
#   >> next_server_number([2.5])
 */
public class NextServer {
    public static void main(String[] args) {
        {
            double[] servers = {1, 3, 5};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {5, 4, 1, 2};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {3, 1, 2};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {3, 2};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {1, 1.5, 2, 2.5, 3, 3.5, 4, 5, 5.5};

            System.out.println(next_server_number(servers));
        }
        {
            double[] servers = {2.5};

            System.out.println(next_server_number(servers));
        }
    }

    private static double next_server_number(double[] servers) {
        Set<Double> set = new HashSet<>();

        for (double server : servers) {
            set.add(server);
        }

        double num = 1.0;
        while (set.contains(num)) {
            ++num;
        }
        return num;
    }
}
