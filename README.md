# Fun-with-Graphs

A project for CS61B: The fundamentals to dynamic data structures, including linear lists, queues, trees, and other linked structures; arrays strings, and hash tables. Storage management. Elementary principles of software engineering. Abstract data types. Algorithms for sorting and searching. Introduction to the Java programming language.

A. Introduction
For this final project, you will be writing a library package to provide facilities for manipulating graphs, plus two clients that use the package. The package must be general: no specifics of either client may find their way into the code for the graph package. We will check that by running our own clients against your package. That is, in contrast to past projects where we didn't care how you arranged the internals of your program as long as it functioned according to the specification, in this case, part of the project code—the API for the graph package—will be set in stone.

B. Client: Make
We've been using the Make program (specifically, GNU make) in most programming assignments this semester. For this project, we are going to implement a much stripped-down version. An input file ("makefile") will consist of rules that indicate that one set of objects (called targets) depends on another set (called prerequisites). Any line that starts with # or contains only tabs and blanks is ignored. For other lines, the syntax is

TT: P1 P2⋯ PmP1 P2⋯ Pm
   command set

where m≥0m≥0, and T starts in the first column. We'll call the first line of the rule its header. Each TT or PjPj is a string of non-whitespace characters other than :, #, or \. The command set consists of zero of more lines that begin with a blank or tab. Command sets with no lines in them are called empty. The commands for one rule end at the next rule (or the end of file). There may be one or more rules that name any particular target, but no more than one of them may be non-empty.

C. Client: Trip finder
I suspect you all have or have used some sort of GPS device by now, or gotten directions from the Googletm map service. Such systems "know" about a network of roads, and given two end points (and perhaps some waypoints in between) will pick out a shortest route. This next client will be a stripped-down version. Specifically, given a map and a request—a sequence of two or more points on the map—it will produce a shortest route from the first point to the second, third, and so forth.

The map file will be in free format (a sequence of "words" containing no whitespace and separated by whitespace—blanks, tabs, newlines, etc.). Information comes in two forms: location entries, introduced by the letter L, and road entries, introduced by the letter R.

In response, your program is supposed to print out a route in this format for each request:

From Berkeley:

1. Take University_Ave west for 0.1 miles.
2. Take Martin_Luther_King_Jr Way south for 1.2 miles.
3. Take Ashby_Ave west for 1.8 miles.
4. Take I-580 west for 1.0 miles.
5. Take I-80 west for 8.4 miles to San_Francisco.
6. Take US-101 south for 34.5 miles.
7. Take CA-85 south for 13.3 miles.
8. Take CA-17 south for 22.2 miles.
9. Take CA-1 north for 1.0 miles to Santa_Cruz.

D. The Graph Package
You should implement these two clients using the graph package whose interface we've supplied. The package graph will export several classes (marked public in the skeleton) and no other public classes. You can add classes to the package as long as they are not public. You can add members to the public classes, as long as they are overridings of public methods or are not public or protected. You can change methods, but not the signatures (types of parameters and return values) of public or protected methods. We will test your graph package separately, exercising methods that might not be used by either of the clients you implement, so unit tests will be particularly useful. Likewise, we will test your clients using our graph package, so that your clients must rely only on the published spec, and not on any special behavior of your particular implementation. 
To make life a bit more interesting, we impose the following restriction: you may not use HashSet, HashMap, Hashtable, TreeSet, or TreeMap in your representations of graphs. You may use any List subtypes you find useful. (In our implementation, we use Java arrays, replacing pointers with array indices, as we did in the CompactLinkedList class in HW 5, but without the fancy pointer-compression trick we used there.) It's OK to use the TreeSet class for the priority queue that is used in implementing Dijkstra's algorithm and A* search. (As you will find, using java.util.PriorityQueue for this purpose won't quite work, since the priorities of queued values change while they are in the queue, requiring that they be removed and reinserted at their new priorities—which is quite a slow operation in a PriorityQueue.)
