# How to use
### Install dependencies
To use this benchmark you need to install the dependencies of python.
```
pip install -r requirements.txt
```

### Build project
The first time when the benchmark are launched, you need to build the jar.
To make this you need to build the project with the flag ``-b``.
```
python3 benchmark.py -b
```

## Run Benchmark
To run a benchmark you can use a various flags.

### Flags
```
-b (--build): If present, build the project and exits
-d (--debug): If present, print log to stdout
-v (--verbose): If present, print with more details
-n (--num_rel_block): Number of the relationships loaded from the dataset file
-f (--file): Path of the dataset file of relationships
-q (--queries): Number of the queries to run
-dp (--depth): Depth of queries between 1 and 10, default is r=random
-lp (--log_path): The path where the logs are saved, default the path of execution of the benchmark
-a (--algorithms): Select algorithm to use with specific depth (j=join, b=traverse-bfs, d=traverse-dfs). Default value: bbbbjjjjddd
-ln (--limit_node_research): Number of the maximum id node to research, default 3000
```

### Benchmark Examples
If you want to run a benchmark with depth 5 using only CSR-BFS, you can use the following command:
```
python3 benchmark.py -v -n 1000 -dp 5 -q 100 -a bbbbbbbbbb
```
With this command you run a benchmark loading only the first 1000 relationships from the dataset file and exec 100 queries with depth 5 resolving that with CSR-BFS algorithm.

If you want to run a benchmark with queries with random depth and with a specific algorithm in a determined depth, you can use:
```
python3 benchmark.py -v -n 1000 -dp r -q 100 -a bbbbjjjjdd
```
The ``-a`` flag specific in a determined position what kind of algorithm that you want to use.
In this case, with ``depth <= 4`` to resolve the query is used the CSR-BFS algorithm and for ``depth >= 9`` is used the CSR-DFS.
For depths between 5 and 8, the query is resolved with HashJoin.