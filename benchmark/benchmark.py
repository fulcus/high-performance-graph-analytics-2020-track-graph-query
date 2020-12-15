import argparse
from subprocess import run, Popen, PIPE
import time
import os
import random
from datetime import datetime

##############################
##############################
from typing import TextIO
from typing.io import IO

DEFAULT_NUM_NODES_BLOCK = 1000
RUN_PATH = os.getcwd() + "/"
PROJECT_PATH = os.path.normpath(os.path.dirname(os.path.realpath(__file__)) + "/../")
TARGET_DIR = PROJECT_PATH + "/target"
TARGET_JAR = TARGET_DIR + "/graph-query-jar-with-dependencies.jar"
FILE_RELATIONSHIPS = "soc-pokec-relationships.txt"
ENCODING = 'utf-8'

##############################
##############################

JAR_BENCH_CMD = "java -jar {} {} {}"


def benchmark(args):
    relationships_file = RUN_PATH + args.file
    if not os.path.isfile(relationships_file):
        log_msg("File of relationships does not exist: " + relationships_file)
        exit(1)

    log_msg("Run benchmark!")
    start_load = time.time_ns()
    jar_bench_cmd = JAR_BENCH_CMD.format(TARGET_JAR, relationships_file, args.num_nodes_block)
    jar = Popen(jar_bench_cmd, shell=True, stdin=PIPE, stdout=PIPE, close_fds=True, cwd=PROJECT_PATH)

    if jar.stdout.readline()[:-1].decode(ENCODING).lower() == "Dataset loaded!".lower():
        log_msg("Dataset loaded!")
    else:
        log_msg("Dataset not loaded!")
        exit(1)
    end_load = time.time_ns()

    if args.debug:
        log_msg(f"Benchmark total loading relationships time: {(end_load - start_load) / 1_000_000_000:.2f} seconds")

    log_msg("#" * 70)

    num_queries = args.queries
    start_queries = time.time_ns()
    start_query = time.time_ns()
    jar.stdin.write(gen_query(args))
    jar.stdin.flush()
    for ln in jar.stdout:
        num_queries -= 1
        log_msg("STDOUT jar: " + ln[:-1].decode(ENCODING))
        end_query = time.time_ns()

        if args.verbose:
            log_msg(f"Benchmark query execution time: {(end_query - start_query) / 1_000_000_000:.2f} seconds")

        log_msg("_" * 70)

        if num_queries > 0:
            start_query = time.time_ns()
            jar.stdin.write(gen_query(args))
            jar.stdin.flush()
        else:
            jar.stdin.write(b"exit\n")
            jar.stdin.flush()
            break

    end_queries = time.time_ns()

    log_msg(f"Benchmark {args.queries} queries execution time: "
            f"{(end_queries - start_queries) / 1_000_000_000:.2f} seconds")


##############################
##############################


def gen_query(args):
    depth = 0
    if args.depth == "r":
        depth = random.randint(2, 11)
    else:
        depth = int(args.depth)

    query = "(a)"
    for i in range(depth):
        query += "->(" + chr(98 + i) + ")"
    log_msg("Depth " + str(depth) + ": " + query)
    return str.encode(query + "\n")


##############################
##############################

log_file: IO
print_stdout = False


def init_log(args):
    global log_file
    log_file = open(args.log_path + datetime.now().strftime("%Y_%m_%d_%H_%M_%S") + ".log", "w+")

    log_file.write("Args of execution benchmark:\n\n")
    for attr, value in args.__dict__.items():
        log_file.write(attr + " : " + str(value) + "\n")

    log_file.write("-" * 70 + "\n")

    print_stdout = args.debug


##############################
##############################

def log_msg(msg):
    log_file.write(msg + "\n")
    if print_stdout:
        print(msg)


##############################
##############################

def end_log():
    print("Created log file: " + log_file.name)
    log_file.close()


##############################
##############################

MVN_BUILD_CMD = "mvn clean compile assembly:single"

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Get args for the benchmark")

    parser.add_argument("-b", "--build", action="store_true",
                        help="If present, build the project")
    parser.add_argument("-d", "--debug", action="store_true", default=False,
                        help="If present, print debug messages")
    parser.add_argument("-v", "--verbose", action="store_false",
                        help="If present, print with more details")
    parser.add_argument("-n", "--num_nodes_block", metavar="N", type=int, default=DEFAULT_NUM_NODES_BLOCK,
                        help="Number of the nodes loaded from the file")
    parser.add_argument("-f", "--file", metavar="N", type=str, default=FILE_RELATIONSHIPS,
                        help="Path of the file of relationships")
    parser.add_argument("-q", "--queries", metavar="N", type=int, default=100,
                        help="Number of the queries to run")
    parser.add_argument("-dp", "--depth", metavar="N", type=str, default="r",
                        help="Depth of queries between 2 and 11, default is random")
    parser.add_argument("-lp", "--log_path", metavar="N", type=str, default=RUN_PATH,
                        help="The path where the logs are saved, default the path of execution of the benchmark")

    # Parse the input arguments;
    args = parser.parse_args()

    init_log(args)

    if args.build:
        result = run(MVN_BUILD_CMD,
                     shell=True,
                     cwd=PROJECT_PATH)
        result.check_returncode()

    print("Benchmarking...")
    benchmark(args)
    print("Benchmark ended!")

    end_log()
